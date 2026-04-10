package com.example.skill_swap

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class SignupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val btn = findViewById<Button>(R.id.btnRegister)

        btn.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }
    }
}