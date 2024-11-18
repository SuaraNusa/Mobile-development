package com.example.suaranusa.ui.musical

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.suaranusa.R
import com.example.suaranusa.databinding.ItemMusicalHeritageBinding

data class MusicalItem(val name: String, val imageResId: Int)

class MusicalHeritageAdapter(private val musicalList: List<MusicalItem>) :
    RecyclerView.Adapter<MusicalHeritageAdapter.MusicalViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicalViewHolder {
        val binding = ItemMusicalHeritageBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MusicalViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MusicalViewHolder, position: Int) {
        val item = musicalList[position]
        holder.bind(item)
    }

    override fun getItemCount() = musicalList.size

    class MusicalViewHolder(private val binding: ItemMusicalHeritageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MusicalItem) {
            binding.textViewInstrumentName.text = item.name
            binding.imageViewInstrument.setImageResource(item.imageResId)
        }
    }
}
