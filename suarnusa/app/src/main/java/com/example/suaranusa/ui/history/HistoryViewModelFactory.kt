package com.example.suaranusa.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.suaranusa.repository.HistoryRepository

class HistoryViewModelFactory (private val repository: HistoryRepository): ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HistoryViewModel::class.java)) {
            return HistoryViewModel(repository) as T
        }
        return super.create(modelClass)
    }
}