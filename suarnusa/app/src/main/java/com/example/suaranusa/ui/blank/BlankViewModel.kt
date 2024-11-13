package com.example.suaranusa.ui.blank

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BlankViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is blank Fragment"
    }

    val text: MutableLiveData<String> = _text
}