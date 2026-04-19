package com.example.skill_swap

import android.app.AlertDialog
import android.graphics.Color
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.RecyclerView

class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(user: User, currentUser: String) {

        val name = itemView.findViewById<TextView>(R.id.tvName)
        val skill = itemView.findViewById<TextView>(R.id.tvSkill)
        val progress = itemView.findViewById<ProgressBar>(R.id.progressBar)
        val btn = itemView.findViewById<Button>(R.id.btnRequest)

        name.text = user.name
        skill.text = "${user.skillHave} → ${user.skillWant}"

        progress.visibility = View.VISIBLE
        when (user.level) {
            "Beginner" -> progress.progress = 35
            "Intermediate" -> progress.progress = 65
            "Expert" -> progress.progress = 100
        }

        btn.text = "Book Slot"

        btn.setOnClickListener {

            val context = itemView.context

            val timeSlots = arrayOf(
                "12 AM","1 AM","2 AM","3 AM","4 AM","5 AM",
                "6 AM","7 AM","8 AM","9 AM","10 AM","11 AM",
                "12 PM","1 PM","2 PM","3 PM","4 PM","5 PM",
                "6 PM","7 PM","8 PM","9 PM","10 PM","11 PM"
            )

            val spinner = Spinner(context)
            spinner.setPadding(20, 20, 20, 20)

            val adapter = ArrayAdapter(
                context,
                android.R.layout.simple_spinner_dropdown_item,
                timeSlots
            )
            spinner.adapter = adapter

            spinner.setBackgroundResource(R.drawable.spinner_bg)
            spinner.setPopupBackgroundDrawable(
                context.getDrawable(R.drawable.spinner_dropdown_bg)
            )

            val dialog = AlertDialog.Builder(context)
                .setTitle("Select Time Slot")
                .setView(spinner)
                .setPositiveButton("Send Request", null)
                .setNegativeButton("Cancel", null)
                .create()

            dialog.show()

            dialog.window?.setBackgroundDrawableResource(R.drawable.dialog_bg)

            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {

                val selectedTime = spinner.selectedItem.toString()
                val allRequests = StorageUtil.getRequests(context)

                for (r in allRequests) {
                    if (r.sender == currentUser &&
                        r.receiver == user.name &&
                        r.skill == user.skillHave
                    ) {
                        Toast.makeText(context, "Already Sent", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                }

                val request = Request(
                    sender = currentUser,
                    receiver = user.name,
                    skill = user.skillHave,
                    status = "Pending",
                    timeSlot = selectedTime
                )

                StorageUtil.saveRequest(context, request)
                Toast.makeText(context, "Request Sent", Toast.LENGTH_SHORT).show()

                dialog.dismiss()
            }

            //bg color of button
            val positiveBtn = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            val negativeBtn = dialog.getButton(AlertDialog.BUTTON_NEGATIVE)

            // Set background color
            positiveBtn.setBackgroundColor(Color.parseColor("#28396C"))
            negativeBtn.setBackgroundColor(Color.parseColor("#28396C"))

            // Set text color
            positiveBtn.setTextColor(Color.parseColor("#EAE6BC"))
            negativeBtn.setTextColor(Color.parseColor("#EAE6BC"))
        }
    }
}