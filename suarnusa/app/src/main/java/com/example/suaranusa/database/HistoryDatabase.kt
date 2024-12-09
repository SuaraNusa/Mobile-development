package com.example.suaranusa.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.suaranusa.dao.HistoryDao
import com.example.suaranusa.model.HistoryItem

@Database(entities = [HistoryItem::class], version = 1)
abstract class HistoryDatabase : RoomDatabase() {
    abstract fun historyDao(): HistoryDao

    companion object {
        @Volatile
        private var INSTANCE: HistoryDatabase? = null

        fun getDatabase(context: Context): HistoryDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HistoryDatabase::class.java,
                    "suaranusa_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}