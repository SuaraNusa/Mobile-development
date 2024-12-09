package com.example.suaranusa.api

import com.example.suaranusa.response.profile.ResponseProfile
import okhttp3.MultipartBody
import retrofit2.http.Field
import retrofit2.http.Multipart
import retrofit2.http.PUT

interface ProfileService {
    @Multipart
    @PUT("users")
    suspend fun editProfile(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("confirmPassword") confirmPassword: String,
        @Field("profile") file: MultipartBody.Part
    ) : ResponseProfile
}