package com.example.suaranusa.ui.profile

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.suaranusa.R

class FavActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        val backButton: ImageView = findViewById(R.id.backButton)

        backButton.setOnClickListener {
            finish()
        }
    }
}
