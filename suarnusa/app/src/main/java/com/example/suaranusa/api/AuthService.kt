package com.example.suaranusa.api

import com.example.suaranusa.resonse.ResponseQuestion
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthService {


    //ADDING FROM LOCAL PROPERTIES
    @GET("")
    fun getQuestions(): Call<ResponseQuestion>
}
