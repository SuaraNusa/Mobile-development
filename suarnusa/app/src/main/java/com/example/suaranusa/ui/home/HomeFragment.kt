package com.example.suaranusa.ui.home

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.suaranusa.R
import com.google.android.material.animation.AnimationUtils

class HomeFragment : Fragment() {

    private lateinit var icon: ImageView
    private lateinit var statusText: TextView
    private lateinit var instructionText: TextView
    private var isListening = false

    companion object {
        private const val RECORD_AUDIO_REQUEST_CODE = 1
        private const val STORAGE_PERMISSION_REQUEST_CODE = 2
    }

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
            requestPermissions()
            Log.i("HomeFragment", "Icon clicked")
        }

        return root
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            RECORD_AUDIO_REQUEST_CODE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // Permission granted
                    requestStoragePermission()
                } else {
                    // Permission denied
                    Toast.makeText(context, "Permission denied to record audio", Toast.LENGTH_SHORT).show()
                }
            }
            STORAGE_PERMISSION_REQUEST_CODE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // Permission granted
                    changeBackgroundState()
                } else {
                    // Permission denied
                    Toast.makeText(context, "Permission denied to access storage", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    private fun requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.MANAGE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.MANAGE_EXTERNAL_STORAGE),
                STORAGE_PERMISSION_REQUEST_CODE
            )
            Log.i("HomeFragment", "Requesting permission storage")
        } else {
            // Permission already granted
            Log.i("HomeFragment", "Permission storage already granted")
            changeBackgroundState()
        }
    }


    private fun requestPermissions() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.RECORD_AUDIO)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.RECORD_AUDIO),
                RECORD_AUDIO_REQUEST_CODE
            )
            Log.i("HomeFragment", "Requesting permission record")
        } else {
            // Permission already granted
            Log.i("HomeFragment", "Permission Record already granted")
            requestStoragePermission()
        }
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