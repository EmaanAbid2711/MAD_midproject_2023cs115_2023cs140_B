package com.example.skill_swap

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        val users = listOf(
            User("Ali", "C++", "Design"),
            User("Sara", "Design", "C++")
        )

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = UserAdapter(users)

        // Navigation Drawer
        val navView = findViewById<NavigationView>(R.id.navigationView)

        navView.setNavigationItemSelectedListener {
            when (it.itemId) {

                R.id.home -> {
                    // already here
                }

                R.id.profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                }

                R.id.sessions -> {
                    startActivity(Intent(this, SessionsActivity::class.java))
                }
            }
            true
        }
    }
}