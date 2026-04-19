package com.example.skill_swap

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val tvName = findViewById<TextView>(R.id.tvName)
        val tvHave = findViewById<TextView>(R.id.tvSkillHave)
        val tvWant = findViewById<TextView>(R.id.tvSkillWant)
        val tvLevel = findViewById<TextView>(R.id.tvLevel)

        val prefs = getSharedPreferences("UserData", MODE_PRIVATE)
        val name = prefs.getString("name", "") ?: ""

        val users = StorageUtil.getUsers(this)

        for (u in users) {
            if (u.name == name) {
                tvName.text = "Name: ${u.name}"
                tvHave.text = "Have: ${u.skillHave}"
                tvWant.text = "Want: ${u.skillWant}"
                tvLevel.text = "Level: ${u.level}"
            }
        }
    }
}