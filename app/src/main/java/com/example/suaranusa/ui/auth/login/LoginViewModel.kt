package com.example.suaranusa.ui.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.suaranusa.repository.AuthRepository
import com.example.suaranusa.response.auth.ResponseAuthLogin
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {
    private val repository = AuthRepository()
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading


     fun loginUser(email: String, password: String):LiveData<ResponseAuthLogin> {
        _isLoading.value = true
         val mutableLiveLogin = MutableLiveData<ResponseAuthLogin>()
        viewModelScope.launch {
            val response = repository.loginUser(email, password)
            _isLoading.value = false
            mutableLiveLogin.value = response

        }
        return mutableLiveLogin
    }

}