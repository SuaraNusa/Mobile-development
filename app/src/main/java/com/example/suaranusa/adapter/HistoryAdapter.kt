package com.example.suaranusa.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.suaranusa.R
import com.example.suaranusa.databinding.ItemHistoryBinding
import com.example.suaranusa.model.HistoryItem
import com.example.suaranusa.repository.HistoryRepository
import com.example.suaranusa.response.predict.ResponsePredict
import com.example.suaranusa.ui.modal.DetailDialogFragment
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class HistoryAdapter(private var historyList: List<HistoryItem>, context: Context) :
    RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

        private val repository:HistoryRepository = HistoryRepository(context)

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
//        notifyDataSetChanged()
    }

   inner class HistoryViewHolder(private val binding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n", "DefaultLocale")
        fun bind(item: HistoryItem) {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
            val outputFormat = SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm", Locale.getDefault())
            val date = inputFormat.parse(item.createdAt)
            val formattedDate = date?.let { outputFormat.format(it) }

            binding.textViewTimeDate.text = formattedDate
            binding.textViewTitle.text = item.predictLabel

            val probabilityPercentage = String.format("%.2f%%", item.predictProb.toDouble() * 100)
            binding.textViewProbability.text = "Prob: $probabilityPercentage"

            binding.favoriteButton.setImageResource(if(item.isFavorite)R.drawable.favoritetrue else R.drawable.favoritefalse)
            binding.favoriteButton.setOnClickListener {
                val newFavorite = !item.isFavorite
                Log.d("Favorite", newFavorite.toString())
                updateIsFavorite(item.id, newFavorite)
                binding.favoriteButton.setImageResource(
                   if(newFavorite) R.drawable.favoritetrue else R.drawable.favoritefalse
                )

                item.isFavorite = newFavorite
            }

            val gson = Gson()
            val responsePredict = gson.fromJson(item.data, ResponsePredict::class.java)

            binding.detailButton.setOnClickListener {
                val dialog = DetailDialogFragment.newInstance(responsePredict)
                dialog.show((binding.root.context as AppCompatActivity).supportFragmentManager, "DetailDialogFragment")
            }
        }
    }

     fun updateIsFavorite(id:Int, isFavorite:Boolean){
        CoroutineScope(Dispatchers.IO).launch {
            repository.updateIsFavorite(id, isFavorite)
        }
    }
}