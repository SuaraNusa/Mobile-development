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
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.suaranusa.R
import com.example.suaranusa.model.HistoryItem
import com.example.suaranusa.repository.HistoryRepository
import com.example.suaranusa.response.predict.ResponsePredict
import com.example.suaranusa.utils.SessionManager
import com.example.suaranusa.utils.jwtDecoder
import com.google.gson.Gson
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ResultFragment : Fragment() {

    private lateinit var sm:SessionManager
    private lateinit var repository: HistoryRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       val root = inflater.inflate(R.layout.fragment_result, container, false)
       val responsePredict =  arguments?.let { ResultFragmentArgs.fromBundle(it).responsePredict }

        sm = SessionManager(requireContext())
        repository = HistoryRepository(requireContext())

        val token = sm.getToken()
        val decodeJWT = jwtDecoder.decode(token?: "")
        val claims = decodeJWT.claims
        val id = claims["id"].toString()
        Log.d("ResultFragment", "Claims: $claims")



        if (id != null) {

            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
            val createdAt = dateFormat.format(Date())
            lifecycleScope.launch {
                repository.insertHistory(
                    HistoryItem(
                        userId = id.toInt(),
                        predictLabel = responsePredict?.data?.songName ?: "",
                        predictProb = responsePredict?.data?.score ?: "",
                        createdAt = createdAt
                    )
                )
            }
        } else {
            Log.e("ResultFragment", "Id claim is null")
        }

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