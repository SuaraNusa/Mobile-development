package com.example.suaranusa.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.suaranusa.repository.InstrumentRepository
import com.example.suaranusa.ui.musicalheritage.MusicalHeritageViewModel

class MusicalHeritageViewModelFactory(private val repository: InstrumentRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MusicalHeritageViewModel::class.java)) {
            return MusicalHeritageViewModel(repository) as T
        }
        return super.create(modelClass)
    }
}