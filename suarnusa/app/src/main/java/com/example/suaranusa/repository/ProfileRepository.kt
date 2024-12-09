package com.example.suaranusa.repository

import android.content.Context
import com.example.suaranusa.BuildConfig
import com.example.suaranusa.api.ProfileService
import com.example.suaranusa.response.profile.ResponseProfile
import com.example.suaranusa.utils.SessionManager
import okhttp3.MultipartBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.HttpException
import java.util.concurrent.TimeUnit

class ProfileRepository (context: Context) {
    private val profileService: ProfileService
    private val sessionManager: SessionManager = SessionManager(context)
    init {
        val token = sessionManager.getToken()
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val client = okhttp3.OkHttpClient.Builder()
            .addInterceptor(logging)
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer $token")
                    .build()
                chain.proceed(request)
            }
            .build()

        val retrofit = retrofit2.Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .client(client)
            .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
            .build()

        profileService = retrofit.create(ProfileService::class.java)
    }

    suspend fun editProfile(name: String, email: String, password: String, confirmPassword: String, profile: MultipartBody.Part) : ResponseProfile {
        return try {
            profileService.editProfile(
                name,
                email,
                password,
                confirmPassword,
                profile
            )
        }catch (e: HttpException){
            if (e.code() == 400){
                ResponseProfile(null, e.message(), e.code().toString())
            }else{
                ResponseProfile(null, e.message(), e.code().toString())
            }
        }
    }
}