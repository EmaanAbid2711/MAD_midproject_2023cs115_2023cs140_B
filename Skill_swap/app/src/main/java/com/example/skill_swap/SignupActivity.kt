package com.example.skill_swap

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class SignupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val name = findViewById<EditText>(R.id.etName)
        val email = findViewById<EditText>(R.id.etEmail)
        val password = findViewById<EditText>(R.id.etPassword)
        val btnSignup = findViewById<Button>(R.id.btnSignup)

        btnSignup.setOnClickListener {

            if (name.text.toString().isEmpty() ||
                email.text.toString().isEmpty() ||
                password.text.toString().isEmpty()
            ) {
                Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show()
            } else {

                val prefs = getSharedPreferences("UserData", MODE_PRIVATE)
                prefs.edit()
                    .putString("name", name.text.toString())
                    .putString("email", email.text.toString())
                    .apply()

                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            }
        }
    }
}