package com.example.suaranusa.ui.auth.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.suaranusa.model.vericationQuestion
import com.example.suaranusa.repository.AuthRepository
import com.example.suaranusa.response.auth.ResponseAuthQuestions
import com.example.suaranusa.response.auth.ResponseAuthRegister
import kotlinx.coroutines.launch

class RegisterViewModel(): ViewModel() {
    private val repository = AuthRepository()
    private val _questions = MutableLiveData<ResponseAuthQuestions>()
    private val _register = MutableLiveData<ResponseAuthRegister>()
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading
    val register: LiveData<ResponseAuthRegister> get() = _register
    val questions: LiveData<ResponseAuthQuestions> get() = _questions

    init {
        fetchQuestions()
    }

    fun fetchQuestions(){

        viewModelScope.launch {
            val result = repository.getQuestions()
            _questions.value = result

        }
    }

    fun registerUser(
        name: String,
        email: String,
        password: String,
        confirmPassword: String,
        verificationQuestions: List<vericationQuestion>
    ) {
        _isLoading.value = true
        viewModelScope.launch {
                val response = repository.registerUser(name,email, password, confirmPassword, verificationQuestions)
            _register.value = response
            _isLoading.value = false
        }
    }



}