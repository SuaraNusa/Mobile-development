package com.example.suaranusa.api

import com.example.suaranusa.response.profile.ResponseProfile
import okhttp3.MultipartBody
import retrofit2.http.Field
import retrofit2.http.Multipart
import retrofit2.http.PUT
import retrofit2.http.Part

interface ProfileService {
    @Multipart
    @PUT("users")
    suspend fun editProfile(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("confirmPassword") confirmPassword: String,
        @Part profile: MultipartBody.Part? = null
    ) : ResponseProfile
}