package com.example.suaranusa.ui.auth

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.suaranusa.utils.SessionManager
import kotlinx.coroutines.launch

class AuthViewModel(private val context:Context):ViewModel() {
    private val sm = SessionManager(context)

    fun checkToken(onTokenValid: () -> Unit, onTokenInvalid: () -> Unit) {
        viewModelScope.launch {
            val token = sm.getToken()
            if (token != null) {
                onTokenValid()
            } else {
                onTokenInvalid()
            }
        }
    }
}