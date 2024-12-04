package com.example.suaranusa.ui.auth.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.suaranusa.repository.AuthRepository
import com.example.suaranusa.response.auth.ResponseAuthQuestions
import kotlinx.coroutines.launch

class RegisterViewModel(): ViewModel() {
    private val repository = AuthRepository()
    private val _questions = MutableLiveData<ResponseAuthQuestions>()
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
}