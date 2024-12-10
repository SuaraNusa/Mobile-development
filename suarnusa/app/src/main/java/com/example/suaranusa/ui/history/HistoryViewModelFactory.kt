package com.example.suaranusa.ui.history

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.suaranusa.repository.HistoryRepository
import com.example.suaranusa.utils.SessionManager

class HistoryViewModelFactory (private val repository: HistoryRepository, private val session: SessionManager): ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HistoryViewModel::class.java)) {
            return HistoryViewModel(repository, session) as T
        }
        return super.create(modelClass)
    }
}