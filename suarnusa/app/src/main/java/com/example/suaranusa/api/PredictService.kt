package com.example.suaranusa.api

import com.example.suaranusa.response.predict.ResponsePredict
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part


interface PredictService {
    @Multipart
    @POST("/predict")
    suspend fun predict(@Part file: MultipartBody.Part):ResponsePredict

}