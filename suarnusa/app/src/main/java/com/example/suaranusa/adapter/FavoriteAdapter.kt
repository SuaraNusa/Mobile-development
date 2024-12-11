package com.example.suaranusa.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.suaranusa.R
import com.example.suaranusa.model.HistoryItem
import com.example.suaranusa.repository.HistoryRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteAdapter(private val repository: HistoryRepository) : ListAdapter<HistoryItem, FavoriteAdapter.FavoriteViewHolder>(FavoriteDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_favorite, parent, false)
        return FavoriteViewHolder(view, repository, this)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val favorite = getItem(position)
        holder.bind(favorite)
    }

    fun removeItem(position: Int) {
        val currentList = currentList.toMutableList()
        currentList.removeAt(position)
        submitList(currentList)
    }

    class FavoriteViewHolder(itemView: View, private val repository: HistoryRepository, private val adapter: FavoriteAdapter) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.textViewTitleFavorite)
        private val probabilityTextView: TextView = itemView.findViewById(R.id.textViewProbabilityFavorite)
        private val dateTextView: TextView = itemView.findViewById(R.id.textViewTimeDate)
        private val favoriteButton: ImageButton = itemView.findViewById(R.id.favoriteButtonFavorite)
        private var isFavorite: Boolean = false

        fun bind(favorite: HistoryItem) {
            nameTextView.text = favorite.predictLabel
            probabilityTextView.text = favorite.predictProb
            dateTextView.text = favorite.createdAt
            isFavorite = favorite.isFavorite

            updateFavoriteIcon()

            favoriteButton.setOnClickListener {
                isFavorite = !isFavorite
                updateFavoriteIcon()
                updateFavoriteStatusInDatabase(favorite)
                if (!isFavorite) {
                    adapter.removeItem(bindingAdapterPosition)
                }
            }
        }

        private fun updateFavoriteIcon() {
            favoriteButton.setImageResource(if (isFavorite) R.drawable.favoritetrue else R.drawable.favoritefalse)
        }

        private fun updateFavoriteStatusInDatabase(favorite: HistoryItem) {
            CoroutineScope(Dispatchers.IO).launch {
                repository.updateIsFavorite(favorite.id, isFavorite)
            }
        }
    }

    class FavoriteDiffCallback : DiffUtil.ItemCallback<HistoryItem>() {
        override fun areItemsTheSame(oldItem: HistoryItem, newItem: HistoryItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: HistoryItem, newItem: HistoryItem): Boolean {
            return oldItem == newItem
        }
    }
}