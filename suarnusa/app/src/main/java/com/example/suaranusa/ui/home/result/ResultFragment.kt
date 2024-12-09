package com.example.suaranusa.ui.home.result

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.suaranusa.R
import com.example.suaranusa.response.predict.ResponsePredict
import com.google.gson.Gson

class ResultFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       val root = inflater.inflate(R.layout.fragment_result, container, false)
       val responsePredict =  arguments?.let { ResultFragmentArgs.fromBundle(it).responsePredict }

        if (responsePredict != null) {
            val data = responsePredict.data

        } else {
            // Handle the case where responsePredict is null
            Log.e("ResultFragment", "ResponsePredict is null")

        }


        val videosContainer = root.findViewById<LinearLayout>(R.id.videos_container)
        val songTitle = root.findViewById<TextView>(R.id.song_title)
        val confident = root.findViewById<TextView>(R.id.confident)

        songTitle.text = responsePredict?.data?.songName
        confident.text = responsePredict?.data?.score.toString()

        responsePredict?.data?.videos?.forEach{videos->
            val videoCard = inflater.inflate(R.layout.card_youtube_link, videosContainer, false)
            val thumbnail = videoCard.findViewById<ImageView>(R.id.thumbnail)
            val title = videoCard.findViewById<TextView>(R.id.card_title)

            title.text = videos?.title
            Glide.with(this)
                .load(videos?.thumbnail?.url)
                .into(thumbnail)

            videoCard.setOnClickListener{
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(videos?.url))
                startActivity(intent)
            }

            videosContainer.addView(videoCard)
        }

        return root
    }

    companion object {
        fun newInstance(responsePredict: ResponsePredict): ResultFragment{
            val fragment = ResultFragment()
            val bundle = Bundle()
            bundle.putString("responsePredict", Gson().toJson(responsePredict))
            fragment.arguments = bundle
            return fragment
        }
    }
}