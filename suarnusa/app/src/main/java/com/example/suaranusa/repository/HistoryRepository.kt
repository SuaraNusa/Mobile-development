package com.example.suaranusa.repository

import android.content.Context
import com.example.suaranusa.model.HistoryItem
import com.example.suaranusa.database.HistoryDatabase

class HistoryRepository(context: Context) {
    private val db = HistoryDatabase.getDatabase(context)

    suspend fun insertHistory(historyItem: HistoryItem) {
        db.historyDao().insertHistory(historyItem)
    }

    suspend fun getHistory(userId: Int): List<HistoryItem> {
        return db.historyDao().getHistory(userId)
    }
}