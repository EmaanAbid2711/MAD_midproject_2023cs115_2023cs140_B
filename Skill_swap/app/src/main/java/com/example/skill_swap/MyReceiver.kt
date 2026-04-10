package com.example.skill_swap

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class MyReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {

        if (intent.action == "SESSION_BOOKED") {
            Toast.makeText(context, "Session Booked Successfully!", Toast.LENGTH_SHORT).show()
        }
    }
}