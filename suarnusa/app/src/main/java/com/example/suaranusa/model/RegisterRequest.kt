package com.example.suaranusa.model

import com.example.suaranusa.response.auth.VerificationQuestionsItem

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String,
    val confirmPassword: String,
    val verificationQuestions: List<VerificationQuestionsItem>,
)
