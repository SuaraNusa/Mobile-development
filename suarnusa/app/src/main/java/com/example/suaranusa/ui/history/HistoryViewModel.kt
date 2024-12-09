package com.example.suaranusa.ui.history

import com.example.suaranusa.repository.HistoryRepository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.suaranusa.model.HistoryItem
import kotlinx.coroutines.launch

class HistoryViewModel(private val repository: HistoryRepository) : ViewModel() {

    private val _historyItems = MutableLiveData<List<HistoryItem>>()
    private val _loading = MutableLiveData<Boolean>()

    val loading: LiveData<Boolean> get() = _loading
    val historyItems: LiveData<List<HistoryItem>> get() = _historyItems

    fun fetchHistoryItems() {
        viewModelScope.launch {
//            _historyItems.value = repository.getHistory(1)
            _historyItems.value = listOf(
                HistoryItem(1, 1, "mykisah", "0.9", "2021-08-01T00:00:00Z"),
                HistoryItem(1, 1, "mykisah", "0.9", "2021-08-01T00:00:00Z"),
                HistoryItem(1, 1, "mykisah", "0.9", "2021-08-01T00:00:00Z"),
                HistoryItem(1, 1, "mykisah", "0.9", "2021-08-01T00:00:00Z")
            )
        }
    }
}