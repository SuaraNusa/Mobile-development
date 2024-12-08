package com.example.suaranusa.repository

import android.content.Context
import com.example.suaranusa.BuildConfig
import com.example.suaranusa.api.PredictService
import com.example.suaranusa.response.predict.ResponsePredict
import com.example.suaranusa.utils.SessionManager
import okhttp3.MultipartBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.HttpException
import java.util.concurrent.TimeUnit

class PredictRepository (context:Context){
    private val predictService : PredictService
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

        predictService = retrofit.create(PredictService::class.java)

    }

    suspend fun predict(file:MultipartBody.Part): ResponsePredict{
        return try {
            predictService.predict(file)
        }catch (e: HttpException){
            if (e.code() == 400){
                ResponsePredict(null, e.message(), e.code().toString())
        }else{
            ResponsePredict(null, e.message(), e.code().toString())
            }
        }
    }



}