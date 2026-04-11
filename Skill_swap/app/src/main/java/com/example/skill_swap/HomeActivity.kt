package com.example.skill_swap

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HomeActivity : AppCompatActivity() {

    private val userList = mutableListOf<User>()
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val prefs = getSharedPreferences("UserData", MODE_PRIVATE)
        val name = prefs.getString("name", "User")

        val tvWelcome = findViewById<TextView>(R.id.tvWelcome)
        val etHave = findViewById<EditText>(R.id.etSkillHave)
        val etWant = findViewById<EditText>(R.id.etSkillWant)
        val btnAdd = findViewById<Button>(R.id.btnAddSkill)
        val recycler = findViewById<RecyclerView>(R.id.recyclerView)

        tvWelcome.text = "Welcome, $name"

        adapter = UserAdapter(userList)
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter

        btnAdd.setOnClickListener {

            val have = etHave.text.toString()
            val want = etWant.text.toString()

            if (have.isEmpty() || want.isEmpty()) {
                Toast.makeText(this, "Enter skills", Toast.LENGTH_SHORT).show()
            } else {

                val user = User(name!!, have, want)
                userList.add(user)

                adapter.notifyDataSetChanged()

                etHave.text.clear()
                etWant.text.clear()

                Toast.makeText(this, "Card Added", Toast.LENGTH_SHORT).show()
            }
        }
    }
}