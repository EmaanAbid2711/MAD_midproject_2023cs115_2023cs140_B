package com.example.skill_swap

import android.content.Context
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.RecyclerView

class RequestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(req: Request, context: Context, list: ArrayList<Request>) {

        val tv = itemView.findViewById<TextView>(R.id.tvRequest)
        val btnAccept = itemView.findViewById<Button>(R.id.btnAccept)
        val btnReject = itemView.findViewById<Button>(R.id.btnReject)

        val prefs = context.getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val currentUser = prefs.getString("name", "")

        if (req.receiver == currentUser) {

            tv.text = "From: ${req.sender} | Skill: ${req.skill}\nTime: ${req.timeSlot} | Status: ${req.status}"

            if (req.status == "Pending") {
                btnAccept.visibility = View.VISIBLE
                btnReject.visibility = View.VISIBLE
            } else {
                btnAccept.visibility = View.GONE
                btnReject.visibility = View.GONE
            }

        } else if (req.sender == currentUser) {

            tv.text = "To: ${req.receiver} | Skill: ${req.skill}\nTime: ${req.timeSlot} | Status: ${req.status}"

            btnAccept.visibility = View.GONE
            btnReject.visibility = View.GONE
        }

        btnAccept.setOnClickListener {
            req.status = "Accepted"
            StorageUtil.updateRequests(context, list)
            Toast.makeText(context, "Accepted", Toast.LENGTH_SHORT).show()
        }

        btnReject.setOnClickListener {
            req.status = "Rejected"
            StorageUtil.updateRequests(context, list)
            Toast.makeText(context, "Rejected", Toast.LENGTH_SHORT).show()
        }
    }
}