package com.example.skill_swap

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class UserAdapter(
    private val list: List<User>,
    private val currentUser: String
) : RecyclerView.Adapter<UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(list[position], currentUser)
    }
}