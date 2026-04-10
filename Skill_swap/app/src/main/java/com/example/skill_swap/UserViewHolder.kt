package com.example.skill_swap

import android.app.AlertDialog
import android.content.Intent
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(user: User) {

        val name = itemView.findViewById<TextView>(R.id.tvName)
        val skill = itemView.findViewById<TextView>(R.id.tvSkill)
        val btn = itemView.findViewById<Button>(R.id.btnBook)

        // Set data
        name.text = user.name
        skill.text = "${user.skillHave} ➝ ${user.skillWant}"

        // Button click
        btn.setOnClickListener {

            val slots = arrayOf("10 AM", "2 PM", "6 PM")

            AlertDialog.Builder(itemView.context)
                .setTitle("Select Slot")
                .setItems(slots) { _, which ->

                    val selected = slots[which]
                    val sessionText = "${user.name} - $selected"

                    // ✅ Save session
                    SessionManager.addSession(sessionText)

                    // ✅ Show message
                    Toast.makeText(
                        itemView.context,
                        "Booked with ${user.name} at $selected",
                        Toast.LENGTH_SHORT
                    ).show()

                    // ✅ FIXED BROADCAST (Explicit Intent)
                    val intent = Intent(itemView.context, MyReceiver::class.java)
                    intent.action = "SESSION_BOOKED"
                    itemView.context.sendBroadcast(intent)
                }
                .show()
        }
    }
}