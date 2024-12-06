package com.example.suaranusa.ui.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.suaranusa.R
import com.google.android.material.animation.AnimationUtils

class HomeFragment : Fragment() {

    private lateinit var icon: ImageView
    private lateinit var statusText: TextView
    private lateinit var instructionText: TextView
    private var isListening = false

    val handler = Handler(Looper.getMainLooper())
    private val repeater = object : Runnable {
        override fun run() {
            if(isListening){
                val scaleUp = android.view.animation.AnimationUtils.loadAnimation(context, R.anim.scale_up)
                val scaleDown = android.view.animation.AnimationUtils.loadAnimation(context, R.anim.scale_down)
                icon.startAnimation(scaleUp)
                scaleUp.setAnimationListener(object : android.view.animation.Animation.AnimationListener {
                    override fun onAnimationStart(animation: android.view.animation.Animation?) {}

                    override fun onAnimationEnd(animation: android.view.animation.Animation?) {
                        icon.startAnimation(scaleDown)
                    }

                    override fun onAnimationRepeat(animation: android.view.animation.Animation?) {}
                })
                handler.postDelayed(this, 1000)
            }
        }
    }

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
            val scaleDown = android.view.animation.AnimationUtils.loadAnimation(context, R.anim.scale_down)
            handler.removeCallbacks(repeater)
            icon.startAnimation(scaleDown)
        } else {
            icon.setBackgroundResource(R.drawable.circle_background_blue)
            statusText.text = "Listening..."
            instructionText.text = "Let our AI do magic"
            handler.post(repeater)
        }
        isListening = !isListening
    }
}