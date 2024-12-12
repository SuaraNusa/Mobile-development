package com.example.suaranusa.repository

import android.util.Log
import com.example.suaranusa.BuildConfig
import com.example.suaranusa.api.AuthService
import com.example.suaranusa.model.LoginRequest
import com.example.suaranusa.model.RegisterRequest
import com.example.suaranusa.model.forgotPasswordRequest
import com.example.suaranusa.model.vericationQuestion
import com.example.suaranusa.response.auth.Data
import com.example.suaranusa.response.auth.ResponseAuthLogin
import com.example.suaranusa.response.auth.ResponseAuthQuestions
import com.example.suaranusa.response.auth.ResponseAuthRegister
import com.example.suaranusa.response.auth.ResponseForgotPassword
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class AuthRepository() {

    private val authService : AuthService

    init {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val client = okhttp3.OkHttpClient.Builder()
            .addInterceptor(logging)
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .build()

        val baseUrl:String = BuildConfig.API_BASE_URL
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
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
        verificationQuestions: List<vericationQuestion>
    ): ResponseAuthRegister {
        return try {
            val request = RegisterRequest(name, email, password, confirmPassword, verificationQuestions)
            val response = authService.registerUser(request)

            Log.d("REP succ", "registerUser: $response")
            response
        } catch (e: HttpException) {
            if (e.code() == 400) {
                val errorBody = e.response()?.errorBody()
                val error = errorBody?.string()
                val errorJson = JSONObject(error)
                val message = errorJson.getString("errors")
                ResponseAuthRegister(null, message, "error")
            } else {
                ResponseAuthRegister(null, "Unknown error", "error")
            }
        }
    }

    suspend fun loginUser(
        email: String,
        password: String
    ):ResponseAuthLogin {
        var responseAuthLogin: ResponseAuthLogin? = null
        return try {
            val request = LoginRequest(email, password)
            responseAuthLogin = authService.loginUser(request)
            Log.d("LOG REP", "loginUser: $responseAuthLogin")
            responseAuthLogin
        } catch (e: HttpException) {
            if (e.code() == 400) {
                val errorBody = e.response()?.errorBody()
                val error = errorBody?.string()
                val errorJson = JSONObject(error)
                val message = errorJson.getString("errors")
                ResponseAuthLogin(null, message, e.code().toString())
            } else if(e.code() == 401){
                val errorBody = e.response()?.errorBody()
                val error = errorBody?.string()
                val errorJson = JSONObject(error)
                val message = errorJson.getString("errors")
                ResponseAuthLogin(null, message, e.code().toString())
            }else {
                ResponseAuthLogin(null, "Unknown error", e.code().toString())
            }
        }
    }

    suspend fun resetPassword(
        email: String,
        password: String,
        confirmPassword: String
    ):ResponseForgotPassword{
        return try {
            val request = forgotPasswordRequest(email, password, confirmPassword)
            val response = authService.resetPassword(request)
            Log.d("REP", "resetPassword: $response")
            response
        }catch (e: Exception){
            Log.d("REP", "resetPassword: ${e.message}")
            ResponseForgotPassword(null, false, "")
        }
    }

}