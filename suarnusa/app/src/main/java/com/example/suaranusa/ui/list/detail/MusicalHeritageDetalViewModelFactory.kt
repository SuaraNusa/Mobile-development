package com.example.suaranusa.ui.list.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.suaranusa.repository.InstrumentRepository

class MusicalHeritageDetalViewModelFactory(private val repository: InstrumentRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
       if (modelClass.isAssignableFrom(MusicalHeritageDetailViewModels::class.java)) {
           @Suppress("UNCHECKED_CAST")
           return MusicalHeritageDetailViewModels(repository) as T

       }
    throw IllegalArgumentException("Unknown ViewModel class")
    }

}