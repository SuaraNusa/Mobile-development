package com.example.suaranusa.model

import com.example.suaranusa.response.auth.Data

data class getQuestions(
    val status: String,
    val error: Boolean,
    val data: List<Data>
)
