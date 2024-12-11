package com.example.suaranusa.utils

import android.os.AsyncTask
import okhttp3.OkHttpClient
import okhttp3.Request
import okio.Okio
import okio.buffer
import okio.sink
import java.io.File
import java.io.IOException

class DownloadImageTask(
    private val onDownloadComplete: (Boolean) -> Unit
) : AsyncTask<String, Void, Boolean>() {

    override fun doInBackground(vararg params: String?): Boolean {
        val url = params[0] ?: return false
        val outputFile = File(params[1] ?: return false)
        return downloadImage(url, outputFile)
    }


    override fun onPostExecute(result: Boolean) {
        onDownloadComplete(result)
    }
}

fun downloadImage(url: String, outputFile: File): Boolean {
    val client = OkHttpClient()
    val request = Request.Builder().url(url).build()

    return try {
        val response = client.newCall(request).execute()
        if (!response.isSuccessful) throw IOException("Failed to download file: $response")

        val sink = outputFile.sink().buffer()
        sink.writeAll(response.body!!.source())
        sink.close()
        true
    } catch (e: IOException) {
        e.printStackTrace()
        false
    }
}