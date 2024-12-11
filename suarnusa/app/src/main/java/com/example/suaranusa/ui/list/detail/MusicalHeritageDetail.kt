package com.example.suaranusa.ui.list.detail

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.suaranusa.R
import com.example.suaranusa.databinding.ActivityMusicalHeritageDetailBinding
import com.example.suaranusa.repository.InstrumentRepository

class MusicalHeritageDetail : AppCompatActivity() {
    private lateinit var binding : ActivityMusicalHeritageDetailBinding
    private val viewModel:MusicalHeritageDetailViewModels by viewModels{
        MusicalHeritageDetalViewModelFactory(InstrumentRepository(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMusicalHeritageDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val imgUrl = "https://storage.googleapis.com/suara-nusa-dev-labs/image-resources/"
        val instrumentId = intent.getStringExtra("instrumentId")?:return
        viewModel.getInstrumentById(instrumentId)

        viewModel.instrument.observe(this,{response ->
            Log.i("Instrument",response.toString())
            val dataItem = response?.data?.first()

            val imagePath = listOf(0, 1, 2, 3).mapNotNull { index ->
                dataItem?.instrumentResources?.getOrNull(index)?.imagePath
            }.firstOrNull() ?: ""

            Glide.with(this)
                .load("$imgUrl$imagePath")
                .into(binding.instrumentImage)

            binding.instrumentName.text = dataItem?.name?: "Unknown Name"
            binding.descriptionText.text = dataItem?.description ?: "No Description"

        })
    }
}