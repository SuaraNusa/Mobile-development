package com.example.suaranusa.model

data class EditProfile(
    val name: String,
    val email: String,
    val password: String,
    val confirmPassword: String,
    val profile: String
)
