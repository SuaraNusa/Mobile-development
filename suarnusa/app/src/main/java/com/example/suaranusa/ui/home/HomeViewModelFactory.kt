package com.example.suaranusa.ui.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.suaranusa.repository.PredictRepository


class HomeViewModelFactory(
    private val repository: PredictRepository,
    private val context:Context
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(repository, context) as T
        }
            throw IllegalArgumentException("Unknown ViewModel class")
    }
}