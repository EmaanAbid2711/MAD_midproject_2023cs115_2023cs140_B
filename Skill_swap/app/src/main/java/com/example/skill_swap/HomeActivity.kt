package com.example.skill_swap

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HomeActivity : AppCompatActivity() {

    lateinit var adapter: UserAdapter
    val matchedUsers = ArrayList<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val recycler = findViewById<RecyclerView>(R.id.recyclerView)
        val skillHave = findViewById<EditText>(R.id.etSkillHave)
        val skillWant = findViewById<EditText>(R.id.etSkillWant)
        val spinner = findViewById<Spinner>(R.id.spinnerLevel)
        val btnAdd = findViewById<Button>(R.id.btnAdd)

        val levels = arrayOf("Beginner", "Intermediate", "Expert")
        spinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, levels)

        val prefs = getSharedPreferences("UserData", MODE_PRIVATE)
        val name = prefs.getString("name", "") ?: ""

        adapter = UserAdapter(matchedUsers, name)
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter

        btnAdd.setOnClickListener {

            val have = skillHave.text.toString()
            val want = skillWant.text.toString()
            val level = spinner.selectedItem.toString()

            if (have.isEmpty() || want.isEmpty()) {
                Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // ADD USER
            val user = User(name, have, want, level)
            UserManager.userList.add(user)

            // MATCHING LOGIC
            matchedUsers.clear()

            for (u in UserManager.userList) {
                if (u.name != name &&
                    u.skillHave == want &&
                    u.skillWant == have) {

                    matchedUsers.add(u)
                }
            }

            adapter.notifyDataSetChanged()
        }
    }
}