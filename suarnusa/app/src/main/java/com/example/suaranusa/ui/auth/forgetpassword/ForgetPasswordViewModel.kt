package com.example.suaranusa.ui.auth.forgetpassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.suaranusa.repository.AuthRepository
import com.example.suaranusa.response.auth.ResponseForgotPassword
import kotlinx.coroutines.launch

class ForgetPasswordViewModel: ViewModel() {
    private val repository = AuthRepository()
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun resetPassword(email: String, password: String, confirmPassword: String): LiveData<ResponseForgotPassword> {
        _isLoading.value = true
        val mutableLiveResetPassword = MutableLiveData<ResponseForgotPassword>()
        viewModelScope.launch {
            val response = repository.resetPassword(email, password, confirmPassword)
            _isLoading.value = false
            mutableLiveResetPassword.value = response
        }
        return mutableLiveResetPassword
    }

}