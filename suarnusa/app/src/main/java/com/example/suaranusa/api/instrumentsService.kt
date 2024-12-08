package com.example.suaranusa.api

import com.example.suaranusa.response.instrument.ResponseInstruments
import retrofit2.http.GET

interface InstrumentsService {
    //using bearer

    @GET("/instruments")
    suspend fun getInstruments(): ResponseInstruments
}