package com.example.quizzapp.model

data class Question(
    val correction_option: String,
    val option1: String,
    val option2: String,
    val option3: String,
    val option4: String,
    val question: String
)