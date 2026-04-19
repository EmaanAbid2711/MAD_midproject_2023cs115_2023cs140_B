package com.example.skill_swap

data class Request(
    var sender: String,
    var receiver: String,
    var skill: String,
    var status: String,
    var timeSlot: String
)