package com.example.suaranusa.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.suaranusa.model.HistoryItem

@Dao
interface HistoryDao {
    @Insert
    suspend fun insertHistory(historyItem: HistoryItem)

    @Query("SELECT * FROM history_search WHERE userId = :userId")
    suspend fun getHistory(userId: Int): List<HistoryItem>
}