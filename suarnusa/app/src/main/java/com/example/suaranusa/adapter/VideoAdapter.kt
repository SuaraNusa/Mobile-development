package com.example.suaranusa.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.suaranusa.databinding.CardYoutubeLinkBinding
import com.example.suaranusa.response.predict.VideosItem
import android.widget.ImageView
import com.bumptech.glide.Glide

class VideoAdapter(private val videoList: List<VideosItem?>) :
    RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val binding = CardYoutubeLinkBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
//
//        binding.root.layoutParams.height = 650
//        binding.root.setBackgroundColor(0x00000000)
        return VideoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val video = videoList[position]
        holder.bind(video)
    }

    override fun getItemCount() = videoList.size

    inner class VideoViewHolder(private val binding: CardYoutubeLinkBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(video: VideosItem?) {
            binding.cardTitle.text = video?.title

            loadImage(binding.thumbnail, (video?.thumbnail?.url ?: "").toString())

            binding.root.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(video?.url))
                binding.root.context.startActivity(intent)
            }
        }
    }

    fun loadImage(imageView: ImageView, url: String) {
        Glide.with(imageView.context)
            .load(url)
            .into(imageView)
    }
}