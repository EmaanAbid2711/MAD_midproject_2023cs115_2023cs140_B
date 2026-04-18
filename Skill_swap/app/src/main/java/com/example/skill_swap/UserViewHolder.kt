package com.example.skill_swap

import android.view.View
import android.widget.*
import androidx.recyclerview.widget.RecyclerView

class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(user: User, currentUser: String) {

        val name = itemView.findViewById<TextView>(R.id.tvName)
        val skill = itemView.findViewById<TextView>(R.id.tvSkill)
        val btn = itemView.findViewById<Button>(R.id.btnRequest)
        val progress = itemView.findViewById<ProgressBar>(R.id.progressBar)

        name.text = user.name
        skill.text = "${user.skillHave} → ${user.skillWant}"

        // PROGRESS BAR
        when (user.level) {
            "Beginner" -> progress.progress = 35
            "Intermediate" -> progress.progress = 65
            "Expert" -> progress.progress = 100
        }

        // SEND REQUEST
        btn.setOnClickListener {

            val request = Request(
                sender = currentUser,
                receiver = user.name,
                skill = user.skillHave,
                status = "Pending"
            )

            RequestManager.requestList.add(request)

            Toast.makeText(itemView.context, "Request Sent", Toast.LENGTH_SHORT).show()
        }
    }
}