package com.example.skill_swap

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        //  2 second delay
        Handler(Looper.getMainLooper()).postDelayed({

            //  go to Login
            startActivity(Intent(this, LoginActivity::class.java))
            finish()

        }, 2000)
    }
}