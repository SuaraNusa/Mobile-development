package com.example.suaranusa.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.suaranusa.databinding.ItemMusicalHeritageBinding

data class MusicalItem(val name: String, val imageResId: String)

class MusicalHeritageAdapter(private var musicalList: List<MusicalItem>) :
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

    fun updateItems(newItems: List<MusicalItem>){
        musicalList = newItems
        notifyDataSetChanged()
    }

    class MusicalViewHolder(private val binding: ItemMusicalHeritageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MusicalItem) {
            binding.textViewInstrumentName.text = item.name
            Glide.with(binding.imageViewInstrument.context)
                .load(item.imageResId)
                .into(binding.imageViewInstrument)
        }
    }
}
