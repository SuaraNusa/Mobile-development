package com.example.suaranusa.ui.main

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.suaranusa.utils.SessionManager

class MainViewModel(private val context: Context):ViewModel() {
    private val sm = SessionManager(context)
    fun checkToken(onTokenValid: () -> Unit, onTokenInvalid: () -> Unit) {
        val token = sm.getToken()
        if (token != null) {
            onTokenValid()
        } else {
            onTokenInvalid()
        }
    }
}