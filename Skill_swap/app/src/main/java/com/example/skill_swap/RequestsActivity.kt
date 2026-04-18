package com.example.skill_swap

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class RequestsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val prefs = getSharedPreferences("UserData", MODE_PRIVATE)
        val currentUser = prefs.getString("name", "")

        for (req in RequestManager.requestList) {

            if (req.receiver == currentUser && req.status == "Pending") {

                AlertDialog.Builder(this)
                    .setTitle("Request")
                    .setMessage("${req.sender} wants ${req.skill}")
                    .setPositiveButton("Accept") { _, _ ->

                        req.status = "Accepted"
                        Toast.makeText(this, "Accepted", Toast.LENGTH_SHORT).show()
                    }
                    .setNegativeButton("Reject") { _, _ ->
                        req.status = "Rejected"
                    }
                    .show()
            }
        }
    }
}