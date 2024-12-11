package com.example.suaranusa.api

import com.example.suaranusa.response.profile.ResponseProfile
import okhttp3.MultipartBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Multipart
import retrofit2.http.PUT
import retrofit2.http.Part

interface ProfileService {
    @Multipart
    @PUT("users")
    suspend fun editProfile(
        @Part("name") name: String,
        @Part("email") email: String,
        @Part("password") password: String,
        @Part("confirmPassword") confirmPassword: String,
        @Part profile: MultipartBody.Part? = null
    ) : ResponseProfile
}