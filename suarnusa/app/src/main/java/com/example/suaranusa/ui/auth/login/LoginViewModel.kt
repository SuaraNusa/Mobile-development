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
    private val _login = MutableLiveData<ResponseAuthLogin>()
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading
    val login: LiveData<ResponseAuthLogin> get() = _login

     fun loginUser(email: String, password: String){
        _isLoading.value = true
        viewModelScope.launch {
            val response = repository.loginUser(email, password)
            _login.value = response
            _isLoading.value = false
        }
    }

}