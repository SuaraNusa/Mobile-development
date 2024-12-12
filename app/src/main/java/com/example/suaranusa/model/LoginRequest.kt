package com.example.suaranusa.model

data class LoginRequest(
    val email: String,
    val password:String
)

data class forgotPasswordRequest(
    val email: String,
    val newPassword:String,
    val confirmationNewPassword:String
)