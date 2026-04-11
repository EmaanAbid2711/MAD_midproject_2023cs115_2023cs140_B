package com.example.skill_swap

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class activity_login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val email = findViewById<EditText>(R.id.etLoginEmail)
        val pass = findViewById<EditText>(R.id.etLoginPass)
        val loginBtn = findViewById<Button>(R.id.btnLogin)
        val goSignup = findViewById<TextView>(R.id.tvGoSignup)

        loginBtn.setOnClickListener {

            val userEmail = email.text.toString().trim()

            if (userEmail.isEmpty()) {
                Toast.makeText(this, "Enter email", Toast.LENGTH_SHORT).show()
            } else {

                // 🔥 SAVE DATA
                val prefs = getSharedPreferences("UserData", MODE_PRIVATE)
                val editor = prefs.edit()
                editor.putString("name", "User")
                editor.putString("email", userEmail)
                editor.apply()

                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            }
        }

        goSignup.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }
    }
}