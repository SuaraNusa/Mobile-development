package com.example.suaranusa.ui.home.result

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import kotlinx.coroutines.launch
import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ResultFragment : Fragment() {

    private lateinit var sm: SessionManager
    private lateinit var repository: HistoryRepository
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_result, container, false)
        val responsePredict = arguments?.let { ResultFragmentArgs.fromBundle(it).responsePredict }

        initializeDependencies()
        handleResponsePredict(responsePredict)
        setupUI(root, responsePredict, inflater)

        val navView = activity?.findViewById<BottomNavigationView>(R.id.nav_view)
        navView?.visibility = View.GONE

        return root
    }

    private fun initializeDependencies() {
        sm = SessionManager(requireContext())
        repository = HistoryRepository(requireContext())
        sharedPreferences = requireContext().getSharedPreferences("ResultFragmentPrefs", Context.MODE_PRIVATE)
    }

    private fun handleResponsePredict(responsePredict: ResponsePredict?) {
        val token = sm.getToken()
        val decodeJWT = jwtDecoder.decode(token ?: "")
        val claims = decodeJWT.claims
        val id = claims["id"]?.asString() ?: return

        val currentHash = generateHash(responsePredict?.data?.score ?: "")

        if (!isHistoryInserted(currentHash)) {
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
                setHistoryInserted(currentHash)
            }
        } else {
            Log.e("ResultFragment", "Id claim is null or history already inserted")
        }
    }

    private fun setupUI(root: View, responsePredict: ResponsePredict?, inflater: LayoutInflater) {
        val videosContainer = root.findViewById<LinearLayout>(R.id.videos_container)
        val songTitle = root.findViewById<TextView>(R.id.song_title)
        val confident = root.findViewById<TextView>(R.id.confident)

        songTitle.text = responsePredict?.data?.songName
        confident.text = responsePredict?.data?.score.toString()

        responsePredict?.data?.videos?.forEach { videos ->
            val videoCard = inflater.inflate(R.layout.card_youtube_link, videosContainer, false)
            val thumbnail = videoCard.findViewById<ImageView>(R.id.thumbnail)
            val title = videoCard.findViewById<TextView>(R.id.card_title)

            title.text = videos?.title
            Glide.with(this)
                .load(videos?.thumbnail?.url)
                .into(thumbnail)

            videoCard.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(videos?.url))
                startActivity(intent)
            }

            videosContainer.addView(videoCard)
        }
    }

    private fun generateHash(input: String): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(input.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }

    private fun isHistoryInserted(hash: String): Boolean {
        return sharedPreferences.getBoolean("history_inserted_$hash", false)
    }

    private fun setHistoryInserted(hash: String) {
        sharedPreferences.edit().putBoolean("history_inserted_$hash", true).apply()
    }

    companion object {
        fun newInstance(responsePredict: ResponsePredict): ResultFragment {
            val fragment = ResultFragment()
            val bundle = Bundle()
            bundle.putString("responsePredict", Gson().toJson(responsePredict))
            fragment.arguments = bundle
            return fragment
        }
    }
}