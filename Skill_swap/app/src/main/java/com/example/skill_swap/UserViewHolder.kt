package com.example.skill_swap

import android.app.AlertDialog
import android.content.Intent
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.RecyclerView

class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(user: User) {

        val name = itemView.findViewById<TextView>(R.id.tvName)
        val skill = itemView.findViewById<TextView>(R.id.tvSkill)
        val btn = itemView.findViewById<Button>(R.id.btnBook)

        name.text = user.name
        skill.text = "${user.skillHave} ➝ ${user.skillWant}"

        btn.setOnClickListener {

            val slots = arrayOf("10 AM", "2 PM", "6 PM")

            AlertDialog.Builder(itemView.context)
                .setTitle("Select Slot")
                .setItems(slots) { _, which ->

                    val selected = slots[which]

                    Toast.makeText(
                        itemView.context,
                        "Booked at $selected",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                .show()
        }
    }
}