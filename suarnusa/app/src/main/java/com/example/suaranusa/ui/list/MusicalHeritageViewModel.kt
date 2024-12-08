package com.example.suaranusa.ui.musicalheritage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.suaranusa.R
import com.example.suaranusa.repository.InstrumentRepository
import com.example.suaranusa.response.instrument.ResponseInstruments
import kotlinx.coroutines.launch

class MusicalHeritageViewModel(private val repository: InstrumentRepository) : ViewModel() {

    private val _instrument = MutableLiveData<ResponseInstruments>()
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading
    val instruments: LiveData<ResponseInstruments> get()  = _instrument

    fun fetchInstruments() {
        viewModelScope.launch {
            _loading.value = true
            try {

                _instrument.value = repository.getInstruments()
            }catch (e: Exception){
                _instrument.value = ResponseInstruments(null, e.message, "error")
            }finally {
                _loading.value = false
            }
        }
    }
}
