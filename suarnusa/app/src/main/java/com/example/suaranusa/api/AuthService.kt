package com.example.suaranusa.api

import com.example.suaranusa.model.LoginRequest
import com.example.suaranusa.model.RegisterRequest
import com.example.suaranusa.model.forgotPasswordRequest
import com.example.suaranusa.response.auth.ResponseAuthLogin
import com.example.suaranusa.response.auth.ResponseAuthQuestions
import com.example.suaranusa.response.auth.ResponseAuthRegister
import com.example.suaranusa.response.auth.ResponseForgotPassword
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthService {
    @GET("/questions")
    suspend fun getQuestions(): ResponseAuthQuestions

    @POST("/authentication/register")
    suspend fun registerUser(@Body register: RegisterRequest): ResponseAuthRegister

    @POST("/authentication/login")
    suspend fun loginUser(@Body login: LoginRequest): ResponseAuthLogin

    @POST("/authentication/reset-password")
    suspend fun resetPassword(@Body reset: forgotPasswordRequest): ResponseForgotPassword
}
