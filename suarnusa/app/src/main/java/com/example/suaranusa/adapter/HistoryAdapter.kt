package com.example.suaranusa.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.suaranusa.databinding.ItemHistoryBinding
import com.example.suaranusa.model.HistoryItem
import java.text.SimpleDateFormat
import java.util.Locale

class HistoryAdapter(private var historyList: List<HistoryItem>) :
    RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = ItemHistoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val item = historyList[position]
        holder.bind(item)
    }

    override fun getItemCount() = historyList.size

    fun updateItems(newItems: List<HistoryItem>){
        historyList = newItems
        notifyDataSetChanged()
    }

    class HistoryViewHolder(private val binding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: HistoryItem) {
            val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
            val formattedDate = dateFormat.format(item.createdAt)

            binding.textViewTimeDate.text = formattedDate
            binding.textViewTitle.text = item.predictLabel
            binding.textViewProbability.text = item.predictProb
        }
    }
}