package com.example.suaranusa.ui.auth.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.suaranusa.model.RegisterRequest
import com.example.suaranusa.repository.AuthRepository
import com.example.suaranusa.response.auth.ResponseAuthQuestions
import com.example.suaranusa.response.auth.ResponseAuthRegister
import com.example.suaranusa.response.auth.VerificationQuestionsItem
import com.google.gson.Gson
import kotlinx.coroutines.launch

class RegisterViewModel(): ViewModel() {
    private val repository = AuthRepository()
    private val _questions = MutableLiveData<ResponseAuthQuestions>()
    private val _register = MutableLiveData<ResponseAuthRegister>()
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
        verificationQuestions: List<VerificationQuestionsItem>
    ) {
        val request = RegisterRequest(name, email, password, confirmPassword, verificationQuestions)
        val json = request.toJson()
        Log.d("REP", "registerUser request: $json")
        viewModelScope.launch {
            try {
                val result = repository.registerUser(name, email, password, confirmPassword, verificationQuestions)
                Log.d("REP REGISTER", "registerUser response: $result")
                _register.value = result
            } catch (e: Exception) {
                Log.e("REP ERROR", "registerUser: ${e.message}", e)
            }
        }
    }

    private fun RegisterRequest.toJson():String{
        return Gson().toJson(this)
    }

}