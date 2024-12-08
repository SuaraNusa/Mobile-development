package com.example.suaranusa.model

import okhttp3.MultipartBody

data class PredictRequest(
    val voice:MultipartBody.Part
)
