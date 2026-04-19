package com.example.skill_swap

import android.content.*
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Base64
import android.view.Gravity
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HomeActivity : AppCompatActivity() {

    lateinit var adapter: UserAdapter
    val matchedUsers = ArrayList<User>()
    lateinit var imgProfile: ImageView

    // ✅ AIRPLANE MODE RECEIVER
    private val airplaneReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == Intent.ACTION_AIRPLANE_MODE_CHANGED) {
                val isOn = isAirplaneModeOn()
                Toast.makeText(
                    this@HomeActivity,
                    if (isOn) "✈ Airplane Mode ON" else "Airplane Mode OFF",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun isAirplaneModeOn(): Boolean {
        return Settings.Global.getInt(contentResolver,
            Settings.Global.AIRPLANE_MODE_ON, 0) != 0
    }

    // ✅ IMAGE PICKER
    val imagePicker = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            val inputStream = contentResolver.openInputStream(uri)
            val bytes = inputStream?.readBytes()
            inputStream?.close()

            if (bytes != null) {
                val base64 = Base64.encodeToString(bytes, Base64.DEFAULT)
                val prefs = getSharedPreferences("UserData", MODE_PRIVATE)
                val name = prefs.getString("name", "") ?: ""

                prefs.edit().putString("profile_$name", base64).apply()

                val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                imgProfile.setImageBitmap(bitmap)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val drawer = findViewById<DrawerLayout>(R.id.drawerLayout)
        val btnMenu = findViewById<TextView>(R.id.btnMenu)
        val btnSearchDrawer = findViewById<Button>(R.id.btnSearchDrawer)
        val btnRequestsDrawer = findViewById<Button>(R.id.btnRequestsDrawer)
        val btnLogoutDrawer = findViewById<Button>(R.id.btnLogoutDrawer)
        val btnProfileDrawer = findViewById<Button>(R.id.btnProfileDrawer)

        val recycler = findViewById<RecyclerView>(R.id.recyclerView)
        val skillHave = findViewById<EditText>(R.id.etSkillHave)
        val skillWant = findViewById<EditText>(R.id.etSkillWant)
        val spinner = findViewById<Spinner>(R.id.spinnerLevel)
        val btnAdd = findViewById<Button>(R.id.btnAdd)

        imgProfile = findViewById(R.id.imgProfile)
        val tvName = findViewById<TextView>(R.id.tvHeaderName)

        val prefs = getSharedPreferences("UserData", MODE_PRIVATE)
        val name = prefs.getString("name", "") ?: ""
        tvName.text = name

        // ✅ LOAD IMAGE
        val savedImage = prefs.getString("profile_$name", null)
        if (savedImage != null) {
            try {
                val bytes = Base64.decode(savedImage, Base64.DEFAULT)
                val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                imgProfile.setImageBitmap(bitmap)
            } catch (e: Exception) {
                imgProfile.setImageResource(R.drawable.ic_launcher_foreground)
            }
        }

        imgProfile.setOnClickListener {
            imagePicker.launch("image/*")
        }

        // ✅ SPINNER
        val levels = arrayOf("Beginner", "Intermediate", "Expert")
        val spinnerAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            levels
        )
        spinnerAdapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )
        spinner.adapter = spinnerAdapter

        // ✅ RECYCLER
        adapter = UserAdapter(matchedUsers, name)
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter

        btnMenu.setOnClickListener {
            drawer.openDrawer(Gravity.START)
        }

        // ✅ SEARCH (FIXED - removes already requested users)
        btnSearchDrawer.setOnClickListener {
            val have = skillHave.text.toString()
            val want = skillWant.text.toString()

            val allUsers = StorageUtil.getUsers(this)
            val allRequests = StorageUtil.getRequests(this)

            matchedUsers.clear()

            for (u in allUsers) {
                if (u.name != name &&
                    u.skillHave.contains(want) &&
                    u.skillWant.contains(have)
                ) {

                    var alreadyRequested = false

                    for (r in allRequests) {
                        if (r.sender == name &&
                            r.receiver == u.name &&
                            r.skill == u.skillHave
                        ) {
                            alreadyRequested = true
                            break
                        }
                    }

                    if (!alreadyRequested) {
                        matchedUsers.add(u)
                    }
                }
            }

            if (matchedUsers.isEmpty()) {
                Toast.makeText(this, "No Match Found", Toast.LENGTH_SHORT).show()
            }

            adapter.notifyDataSetChanged()
            drawer.closeDrawer(Gravity.START)
        }

        // ✅ REQUESTS
        btnRequestsDrawer.setOnClickListener {
            startActivity(Intent(this, RequestsActivity::class.java))
        }

        // ✅ PROFILE
        btnProfileDrawer.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
            drawer.closeDrawer(Gravity.START)
        }

        // ✅ LOGOUT
        btnLogoutDrawer.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        // ✅ ADD SKILL (VALIDATION + APPEND)
        btnAdd.setOnClickListener {

            val have = skillHave.text.toString().trim()
            val want = skillWant.text.toString().trim()
            val level = spinner.selectedItem.toString()

            // ❌ VALIDATION
            if (have.isEmpty() || want.isEmpty()) {
                Toast.makeText(this, "Please add skill", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val users = StorageUtil.getUsers(this)

            for (i in users.indices) {
                if (users[i].name == name) {

                    val oldHave = users[i].skillHave
                    val oldWant = users[i].skillWant
                    val oldLevel = users[i].level

                    val newHave =
                        if (oldHave.isNotEmpty()) "$oldHave, $have" else have
                    val newWant =
                        if (oldWant.isNotEmpty()) "$oldWant, $want" else want
                    val newLevel =
                        if (oldLevel.isNotEmpty()) "$oldLevel, $level" else level

                    users[i] = User(
                        name,
                        users[i].email,
                        users[i].password,
                        newHave,
                        newWant,
                        newLevel
                    )
                }
            }

            StorageUtil.updateUsers(this, users)

            Toast.makeText(this, "Skill Added", Toast.LENGTH_SHORT).show()

            // ✅ clear fields
            skillHave.text.clear()
            skillWant.text.clear()
        }
    }

    override fun onResume() {
        super.onResume()
        registerReceiver(
            airplaneReceiver,
            IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED)
        )
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(airplaneReceiver)
    }
}