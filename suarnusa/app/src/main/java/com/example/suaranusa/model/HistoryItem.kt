package com.example.suaranusa.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history_search")
data class HistoryItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val userId: Int,
    val predictLabel: String,
    val predictProb: String,
    val createdAt: String
)