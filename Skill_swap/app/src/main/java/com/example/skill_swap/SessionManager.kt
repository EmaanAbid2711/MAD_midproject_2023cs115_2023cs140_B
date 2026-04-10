package com.example.skill_swap

object SessionManager {

    val sessionList = mutableListOf<String>()

    fun addSession(session: String) {
        sessionList.add(session)
    }

    fun getSessions(): List<String> {
        return sessionList
    }
}