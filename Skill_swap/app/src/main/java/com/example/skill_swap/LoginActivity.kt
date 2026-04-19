package com.example.skill_swap

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnGoSignup = findViewById<Button>(R.id.btnGoSignup)

        btnLogin.setOnClickListener {

            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            val users = StorageUtil.getUsers(this)

            var found = false

            for (u in users) {
                if (u.email == email && u.password == password) {

                    found = true

                    val prefs = getSharedPreferences("UserData", MODE_PRIVATE)
                    prefs.edit().putString("name", u.name).apply()

                    Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()

                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()
                    break
                }
            }

            if (!found) {
                Toast.makeText(this, "Invalid Email or Password", Toast.LENGTH_SHORT).show()
            }
        }

        btnGoSignup.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
            finish()
        }
    }
}