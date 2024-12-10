package com.example.suaranusa.api

import com.example.suaranusa.response.instrument.ResponseInstruments
import retrofit2.http.GET
import retrofit2.http.Path

interface InstrumentsService {
    //using bearer

    @GET("/instruments")
    suspend fun getInstruments(): ResponseInstruments

    @GET("/instruments/{id}")
    suspend fun getInstrumentById(@Path("id") id:String):ResponseInstruments
}