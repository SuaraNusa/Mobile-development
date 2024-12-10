package com.example.suaranusa.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.request.target.Target
import com.example.suaranusa.BuildConfig
import com.example.suaranusa.databinding.ItemMusicalHeritageBinding
import com.example.suaranusa.utils.SessionManager

data class MusicalItem(val name: String, val imageResId: String)

class MusicalHeritageAdapter(private var musicalList: List<MusicalItem>,context: Context ) :
    RecyclerView.Adapter<MusicalHeritageAdapter.MusicalViewHolder>() {

    private val sm = SessionManager(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicalViewHolder {
        val binding = ItemMusicalHeritageBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MusicalViewHolder(binding, sm)
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

    class MusicalViewHolder(private val binding: ItemMusicalHeritageBinding, val sm:SessionManager) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MusicalItem) {
            binding.textViewInstrumentName.text = item.name
            Log.d("MusicalHeritageAdapter", "Item Image: ${item.imageResId}")
            val imgUrl = "https://storage.googleapis.com/suara-nusa-dev-labs/image-resources/${item.imageResId}"
            Log.d("MusicalHeritageAdapter", "bind: $imgUrl")

            val glideUrl = GlideUrl(
                imgUrl,
//                LazyHeaders.Builder()
//                    .addHeader("Authorization", "Bearer ${sm.getToken()}")
//                    .build()
            )

            Glide.with(binding.imageViewInstrument.context)
                .load(glideUrl)
                .listener(object : com.bumptech.glide.request.RequestListener<android.graphics.drawable.Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>,
                        isFirstResource: Boolean
                    ): Boolean {
                        // log exception
                        Log.e("MusicalHeritageAdapter", "onLoadFailed: ", e)
                        return false
                    }
                    override fun onResourceReady(
                        resource: Drawable,
                        model: Any,
                        target: Target<Drawable>?,
                        dataSource: DataSource,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }
                })
                .into(binding.imageViewInstrument)
        }
    }
}
