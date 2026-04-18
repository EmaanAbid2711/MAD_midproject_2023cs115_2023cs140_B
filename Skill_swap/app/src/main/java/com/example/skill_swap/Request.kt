package com.example.skill_swap

data class Request(
    val sender: String,
    val receiver: String,
    val skill: String,
    var status: String
)