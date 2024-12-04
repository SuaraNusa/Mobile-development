package com.example.suaranusa.repository

import android.util.Log
import com.example.suaranusa.BuildConfig
import com.example.suaranusa.api.AuthService
import com.example.suaranusa.model.RegisterRequest
import com.example.suaranusa.response.auth.Data
import com.example.suaranusa.response.auth.ResponseAuthQuestions
import com.example.suaranusa.response.auth.ResponseAuthRegister
import com.example.suaranusa.response.auth.VerificationQuestionsItem
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
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

    suspend fun registerUser(
        name: String,
        email: String,
        password: String,
        confirmPassword: String,
        verificationQuestions: List<VerificationQuestionsItem>,
    ): ResponseAuthRegister {
        //dummy
        var response = ResponseAuthRegister("", emptyList(),"","","")
       val JsonBody = JSONObject()
        JsonBody.put("name", name)
        JsonBody.put("email", email)
        JsonBody.put("password", password)
        JsonBody.put("confirm_password", confirmPassword)
        JsonBody.put("verification_questions", verificationQuestions)
        val requestBody = JsonBody.toString().toRequestBody()
        return try {
            response = authService.registerUser(requestBody)
            Log.d("REP succ", "registerUser: $response")
            response
        }catch (e: Exception){
            Log.e("REP err", "registerUser: ${e.message}")
            response
        }

    }

}