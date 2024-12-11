package com.example.suaranusa.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.suaranusa.model.HistoryItem

@Dao
interface HistoryDao {
    @Insert
    suspend fun insertHistory(historyItem: HistoryItem)

    @Query("SELECT * FROM history_search WHERE userId = :userId")
     fun getHistory(userId: Int): LiveData<List<HistoryItem>>

    @Query("UPDATE history_search SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun isFavorite(id: Int, isFavorite: Boolean)

    @Query("SELECT * FROM history_search WHERE isFavorite = 1 AND userId = :userId")
    fun getFavorite(userId: Int): LiveData<List<HistoryItem>>
}