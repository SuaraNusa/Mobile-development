package com.example.suaranusa.ui.home

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.suaranusa.R
import com.example.suaranusa.repository.PredictRepository
import com.example.suaranusa.ui.home.result.ResultFragment

class HomeFragment : Fragment() {

    private lateinit var icon: ImageView
    private lateinit var statusText: TextView
    private lateinit var instructionText: TextView
    private var isListening = false
    private lateinit var progressBar: ProgressBar
    private val homeViewModel: HomeViewModel by viewModels { HomeViewModelFactory(PredictRepository(requireContext()), requireContext()) }
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
        progressBar = root.findViewById(R.id.loading_bar)

        homeViewModel.isRecording.observe(viewLifecycleOwner, {
            if(!it){
                stopRecordingAnim()
            }
        })

        homeViewModel.isLoading.observe(viewLifecycleOwner,{isLoading->
            if(isLoading){
                progressBar.visibility = View.VISIBLE
            }else{
                progressBar.visibility = View.GONE
            }
        })

        homeViewModel.isError.observe(viewLifecycleOwner,{ isError->
            if(isError != null){
                AlertDialog.Builder(requireContext())
                    .setTitle("Error")
                    .setMessage(isError)
                    .setPositiveButton("OK", null)
                    .show()
            }

        })

        homeViewModel.responsePredict.observe(viewLifecycleOwner,{ responsePredict->
                responsePredict.let {
                    val action = HomeFragmentDirections.actionNavigationHomeToResultFragment(responsePredict)
                    findNavController().navigate(action)
                }
        })


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
                if (grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                    // Permissions granted
                    requestStoragePermission()
                } else {
                    // Permissions denied
                    Toast.makeText(context, "Permissions denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun requestStoragePermission() {
       if(SDK_INT >= Build.VERSION_CODES.TIRAMISU){

            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_MEDIA_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.READ_MEDIA_AUDIO),
                    STORAGE_PERMISSION_REQUEST_CODE
                )
                Log.i("HomeFragment", "Requesting permission storage")
            } else {
                // Permission already granted
                Log.i("HomeFragment", "Permission Storage already granted")
                changeBackgroundState()
                homeViewModel.startRecording()
            }

       }else{

              if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_MEDIA_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                     requireActivity(),
                     arrayOf(Manifest.permission.READ_MEDIA_AUDIO),
                     STORAGE_PERMISSION_REQUEST_CODE
                )
                Log.i("HomeFragment", "Requesting permission storage")
              } else {
                // Permission already granted
                Log.i("HomeFragment", "Permission Storage already granted")
                changeBackgroundState()
                  homeViewModel.startRecording()
              }
       }
    }




    private fun requestPermissions() {
        val permissions = mutableListOf<String>()
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.RECORD_AUDIO)
            != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.RECORD_AUDIO)
        }
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_MEDIA_AUDIO)
            != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.READ_MEDIA_AUDIO)
        }
        if (permissions.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                permissions.toTypedArray(),
                RECORD_AUDIO_REQUEST_CODE
            )
            Log.i("HomeFragment", "Requesting permissions: $permissions")
        } else {
            // Permissions already granted
            Log.i("HomeFragment", "Permissions already granted")
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

    private fun stopRecordingAnim(){
        icon.setBackgroundResource(R.drawable.circle_background_grey)
        statusText.text = "Search Song"
        instructionText.text = "Tap to start recording and find traditional songs"
        val scaleDown = android.view.animation.AnimationUtils.loadAnimation(context, R.anim.scale_down)
        icon.startAnimation(scaleDown)
        handler.removeCallbacks(repeater)
        isListening = false
    }
}