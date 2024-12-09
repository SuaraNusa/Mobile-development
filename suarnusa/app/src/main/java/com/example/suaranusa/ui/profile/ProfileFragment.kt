package com.example.suaranusa.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.suaranusa.R
import com.example.suaranusa.ui.profile.BlankFragment

class ProfileFragment : Fragment() {

    private lateinit var backButton: ImageView
    private lateinit var editButton: ImageView
    private lateinit var profileName: TextView
    private lateinit var profileEmail: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        backButton = view.findViewById(R.id.backButton)
        editButton = view.findViewById(R.id.editButton)
        profileName = view.findViewById(R.id.profileName)
        profileEmail = view.findViewById(R.id.profileEmail)

        profileName.text = "Nama Kamu"
        profileEmail.text = "your.email@gmail.com"

        backButton.setOnClickListener {
            val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.container, BlankFragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }

        editButton.setOnClickListener {
            val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.container, EditProfileFragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }

        return view
    }
}
