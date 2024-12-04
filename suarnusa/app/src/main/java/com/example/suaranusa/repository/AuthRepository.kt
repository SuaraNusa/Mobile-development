package com.example.suaranusa.repository

import android.util.Log
import com.example.suaranusa.BuildConfig
import com.example.suaranusa.api.AuthService
import com.example.suaranusa.response.auth.Data
import com.example.suaranusa.response.auth.ResponseAuthQuestions
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class AuthRepository() {
    private val authService : AuthService
    init {
        val baseUrl = BuildConfig.API_BASE_URL

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        authService = retrofit.create(AuthService::class.java)
    }



    suspend fun getQuestions(): ResponseAuthQuestions {
        return try {
            val response = authService.getQuestions()
            Log.d("REP", "getQuestions: $response")
            ResponseAuthQuestions(response.data, response.errors, response.status)

        }catch (e: Exception){
            Log.d("REP", "getQuestions: ${e.message}")
            ResponseAuthQuestions(Data(emptyList()), false, "")
        }


    }


}