package com.example.suaranusa.model

import com.example.suaranusa.response.auth.DataItem


data class vericationQuestion(
    val verificationQuestionId: Int,
    val answer: String
)


data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String,
    val confirmPassword: String,
    val verificationQuestions: List<vericationQuestion>,
)
