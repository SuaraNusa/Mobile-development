package com.example.suaranusa.utils

import android.content.Context
import okhttp3.OkHttpClient
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


fun downloadFileToData(context:Context,username:String):File?{
    val url = "https://ui-avatars.com/api/?name=$username.jpg"
    val client = OkHttpClient()
    val request = okhttp3.Request.Builder().url(url).build()

    return try {
        val response = client.newCall(request).execute()
        if (!response.isSuccessful) throw IOException("Unexpected code $response")

        val inputStream = response.body?.byteStream()
        val dataFile = File(context.filesDir, "profile.jpg")
        val outputStream = FileOutputStream(dataFile)

        inputStream?.use { input ->
            outputStream.use { output ->
                input.copyTo(output)
            }
        }

        dataFile
    }catch (e:Exception){
        e.printStackTrace()
        null
    }
}