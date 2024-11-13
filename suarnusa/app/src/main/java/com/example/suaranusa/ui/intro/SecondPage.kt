package com.example.suaranusa.ui.intro

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.suaranusa.MainActivity
import com.example.suaranusa.databinding.IntroSecondPageBinding

class SecondPage : Fragment() {

    private lateinit var binding: IntroSecondPageBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = IntroSecondPageBinding.inflate(inflater, container, false)

        binding.getStarted.setOnClickListener {
            //close the fragment
            val sharedPreferences = requireActivity().getSharedPreferences("isIntroShow", Context.MODE_PRIVATE)
            with(sharedPreferences.edit()){
                putBoolean("isIntroShow", true)
                apply()
            }
            //isIntroShow get value from sharedPreferences
            val isIntroShow = sharedPreferences.getBoolean("isIntroShow", false)
            Log.d("isIntroShow", isIntroShow.toString())
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
            activity?.finish()

        }

        return binding.root


    }


}