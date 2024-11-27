package com.example.suaranusa.model

import com.example.suaranusa.resonse.ResponseQuestion

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String,
    val confirmPassword: String,
    val verificationQuestions: Int,
    val answer: String
)
