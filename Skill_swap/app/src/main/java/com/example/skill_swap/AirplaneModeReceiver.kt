package com.example.skill_swap

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.widget.Toast

class AirplaneModeReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        if (intent.action == Intent.ACTION_AIRPLANE_MODE_CHANGED) {

            val isOn = Settings.Global.getInt(
                context.contentResolver,
                Settings.Global.AIRPLANE_MODE_ON, 0
            ) != 0

            if (isOn) {
                Toast.makeText(context, "Airplane Mode ON ✈️", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Airplane Mode OFF 📶", Toast.LENGTH_SHORT).show()
            }
        }
    }
}