package com.example.suaranusa.repository

import android.content.Context
import android.util.Log
import com.example.suaranusa.BuildConfig
import com.example.suaranusa.api.ProfileService
import com.example.suaranusa.response.profile.ResponseProfile
import com.example.suaranusa.utils.SessionManager
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import okio.Buffer
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit

class ProfileRepository(private val context: Context) {
    private val profileService: ProfileService
    private val sessionManager: SessionManager = SessionManager(context)
    private lateinit var token: String
    init {
        token = sessionManager.getToken()?.trim()?: ""
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

        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        profileService = retrofit.create(ProfileService::class.java)
    }

    fun RequestBody.string():String{
        val buffer = Buffer()
        this.writeTo(buffer)
        return buffer.readUtf8()
    }


    suspend fun editProfile(
        name: RequestBody,
        email: RequestBody,
        password: RequestBody,
        confirmPassword: RequestBody,
        profile: MultipartBody.Part
    ): ResponseProfile {

        return try {
            profileService.editProfile(name, email, password, confirmPassword, profile)
        } catch (e: HttpException) {
            // Tangani kesalahan HTTP
            ResponseProfile(null, "HTTP error: ${e.message}", e.code().toString())
        } catch (e: IOException) {
            // Tangani kesalahan koneksi
            ResponseProfile(null, "Network error: ${e.message}", "network_error")
        } catch (e: Exception) {
            // Tangani kesalahan tak terduga lainnya
            ResponseProfile(null, "Unexpected error: ${e.message}", "unexpected_error")
        }
    }

}
