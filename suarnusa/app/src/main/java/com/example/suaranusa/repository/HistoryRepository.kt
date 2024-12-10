package com.example.suaranusa.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.suaranusa.model.HistoryItem
import com.example.suaranusa.database.AppDatabase

class HistoryRepository(context: Context) {
    private val db = AppDatabase.getDatabase(context)

    suspend fun insertHistory(historyItem: HistoryItem) {
        db.historyDao().insertHistory(historyItem)
    }

     fun getHistory(userId: Int): LiveData<List<HistoryItem>> {
        return db.historyDao().getHistory(userId)
    }

    suspend fun updateIsFavorite(id: Int, isFavorite: Boolean) {
        db.historyDao().isFavorite(id, isFavorite)
    }
}