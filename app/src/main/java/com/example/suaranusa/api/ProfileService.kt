package com.example.suaranusa.api

import com.example.suaranusa.response.profile.ResponseProfile
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Multipart
import retrofit2.http.PUT
import retrofit2.http.Part

interface ProfileService {
    @Multipart
    @PUT("users")
    suspend fun editProfile(
        @Part("name") name : RequestBody,
        @Part("email") email: RequestBody,
        @Part("password") password: RequestBody,
        @Part("confirmPassword") confirmPassword: RequestBody,
        @Part profile: MultipartBody.Part? = null
    ) : ResponseProfile
}