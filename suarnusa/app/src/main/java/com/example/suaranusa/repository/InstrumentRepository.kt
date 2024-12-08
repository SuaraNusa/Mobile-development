package com.example.suaranusa.repository

import android.content.Context
import com.example.suaranusa.BuildConfig
import com.example.suaranusa.api.InstrumentsService
import com.example.suaranusa.response.instrument.ResponseInstruments
import com.example.suaranusa.utils.SessionManager
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class InstrumentRepository(context:Context) {

    private val instrumentsService: InstrumentsService
    private val sessionManager: SessionManager = SessionManager(context)


    init {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val token = sessionManager.getToken()?: ""
        val client = okhttp3.OkHttpClient.Builder()
            .addInterceptor(logging)
            .connectTimeout(20,TimeUnit.SECONDS)
            .readTimeout(20,TimeUnit.SECONDS)
            .writeTimeout(20,TimeUnit.SECONDS)
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer $token")
                    .build()
                chain.proceed(request)
            }
            .build()

        val baseUrl:String = BuildConfig.API_BASE_URL
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        instrumentsService= retrofit.create(InstrumentsService::class.java)
    }


    suspend fun getInstruments() :ResponseInstruments{
        return try {

            val response = instrumentsService.getInstruments()
            ResponseInstruments(response.data, response.errors, response.status)
        }catch (e: HttpException){
            if(e.code() == 400){
                ResponseInstruments(null, e.message(), e.code().toString())
            }else{
                ResponseInstruments(null, e.message(), e.code().toString())
            }
        }
    }


}