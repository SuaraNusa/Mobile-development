package com.example.suaranusa.api

import com.example.suaranusa.response.auth.ResponseAuthQuestions
import retrofit2.http.GET

interface AuthService {
    @GET("/questions")
    suspend fun getQuestions(): ResponseAuthQuestions


}
