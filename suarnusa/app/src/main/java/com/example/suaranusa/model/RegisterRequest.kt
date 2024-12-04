package com.example.suaranusa.model

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String,
    val confirmPassword: String,
    val verificationQuestions: Int,
    val answer: String
)
