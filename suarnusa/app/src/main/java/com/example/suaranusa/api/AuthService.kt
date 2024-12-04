package com.example.suaranusa.api

import com.example.suaranusa.response.auth.ResponseAuthQuestions
import com.example.suaranusa.response.auth.ResponseAuthRegister
import okhttp3.RequestBody
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthService {
    @GET("/questions")
    suspend fun getQuestions(): ResponseAuthQuestions

    @POST("/authentication/register")
    suspend fun registerUser(@Body request: RequestBody): ResponseAuthRegister
}
