package com.example.skill_swap

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SessionsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sessions)

        val recycler = findViewById<RecyclerView>(R.id.sessionRecycler)
        val emptyText = findViewById<TextView>(R.id.tvEmpty)

        val sessions = SessionManager.getSessions()

        if (sessions.isEmpty()) {
            // 👉 Show "No sessions yet"
            emptyText.visibility = View.VISIBLE
            recycler.visibility = View.GONE
        } else {
            // 👉 Show RecyclerView
            emptyText.visibility = View.GONE
            recycler.visibility = View.VISIBLE

            recycler.layoutManager = LinearLayoutManager(this)
            recycler.adapter = SessionAdapter(sessions)
        }
    }
}