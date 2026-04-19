package com.example.skill_swap

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RequestsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_requests)

        val recycler = findViewById<RecyclerView>(R.id.recyclerRequests)

        val prefs = getSharedPreferences("UserData", MODE_PRIVATE)
        val currentUser = prefs.getString("name", null)

        if (currentUser == null) {
            Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val allRequests = StorageUtil.getRequests(this)
        val myRequests = ArrayList<Request>()

        for (r in allRequests) {
            if (r.sender == currentUser || r.receiver == currentUser) {
                myRequests.add(r)
            }
        }

        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = RequestAdapter(myRequests, this)
    }
}