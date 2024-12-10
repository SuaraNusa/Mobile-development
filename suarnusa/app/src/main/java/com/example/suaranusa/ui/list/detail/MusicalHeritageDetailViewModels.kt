package com.example.suaranusa.ui.list.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.suaranusa.repository.InstrumentRepository
import com.example.suaranusa.response.instrument.ResponseInstruments
import kotlinx.coroutines.launch

class MusicalHeritageDetailViewModels(private val repository: InstrumentRepository):ViewModel() {
    private val _instrument = MutableLiveData<ResponseInstruments>()
    val instrument get() = _instrument

    fun getInstrumentById(id:String){
        viewModelScope.launch {
            val response = repository.getInstrumentById(id)
            _instrument.postValue(response)
        }
    }
}