package com.example.suaranusa.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.suaranusa.repository.PredictRepository
import com.example.suaranusa.response.predict.ResponsePredict
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class HomeViewModel(private val repository: PredictRepository) : ViewModel() {

    private val _responsePredict = MutableLiveData<ResponsePredict>()
    val responsePredict: LiveData<ResponsePredict> get() = _responsePredict

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    fun fetchInstrument(file: MultipartBody.Part){
        viewModelScope.launch {
            try {
                _responsePredict.value = repository.predict(file)
            }catch (e: Exception){
                _responsePredict.value = ResponsePredict(null, e.message, "error")
            }
        }
    }
}