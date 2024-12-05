package com.example.suaranusa.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.suaranusa.R

class HomeFragment : Fragment() {

    private lateinit var icon: ImageView
    private lateinit var statusText: TextView
    private lateinit var instructionText: TextView
    private var isListening = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        icon = root.findViewById(R.id.icon)
        statusText = root.findViewById(R.id.status_text)
        instructionText = root.findViewById(R.id.instruction_text)

        icon.setOnClickListener {
            changeBackgroundState()
        }

        return root
    }

    private fun changeBackgroundState() {
        if (isListening) {
            icon.setBackgroundResource(R.drawable.circle_background_grey)
            statusText.text = "Search Song"
            instructionText.text = "Tap to start recording and find traditional songs"
        } else {
//            icon.setBackgroundResource(R.anim.button_transition)
            icon.setBackgroundResource(R.drawable.circle_background_blue)
//            val transition = icon.background as TransitionDrawable
//            transition.startTransition(300)
            statusText.text = "Listening..."
            instructionText.text = "Let our AI do magic"
        }
        isListening = !isListening
    }
}