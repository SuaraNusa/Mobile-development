package com.example.suaranusa.ui.profile

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.suaranusa.R
import com.example.suaranusa.adapter.FavoriteAdapter
import com.example.suaranusa.database.AppDatabase
import com.example.suaranusa.repository.HistoryRepository


class FavActivity : AppCompatActivity() {
    private lateinit var favoriteRecyclerView: RecyclerView
    private lateinit var favoriteAdapter: FavoriteAdapter
    private lateinit var historyRepository: HistoryRepository


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        supportActionBar?.hide()

        val backButton: ImageView = findViewById(R.id.backButton)
        backButton.setOnClickListener {
            finish()
        }

        historyRepository = HistoryRepository(this)

        favoriteRecyclerView = findViewById(R.id.favoriteListView)
        favoriteRecyclerView.layoutManager = LinearLayoutManager(this)
        favoriteAdapter = FavoriteAdapter(historyRepository)
        favoriteRecyclerView.adapter = favoriteAdapter

        val userId = intent.getIntExtra("USER_ID", -1)
        if (userId != -1) {
            val db = AppDatabase.getDatabase(this)
            db.historyDao().getFavorite(userId).observe(this) { favorites ->
                favoriteAdapter.submitList(favorites)
            }
        }
    }
}