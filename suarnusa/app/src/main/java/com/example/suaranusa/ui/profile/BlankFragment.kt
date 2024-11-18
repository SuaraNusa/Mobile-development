package com.example.suaranusa.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import com.example.suaranusa.R
import com.example.suaranusa.TabLayout
import com.example.suaranusa.ui.auth.LoginFragment

class BlankFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_blank, container, false)

        val myProfileSection: LinearLayout = view.findViewById(R.id.myProfileSection)
        val favoriteSection: LinearLayout = view.findViewById(R.id.favoriteSection)
        val logoutButton = view.findViewById<Button>(R.id.logoutButton)

        myProfileSection.setOnClickListener {
            val intent = Intent(activity, EditProfileActivity::class.java)
            startActivity(intent)
        }

        favoriteSection.setOnClickListener {
            val intent = Intent(activity, FavoriteActivity::class.java)
            startActivity(intent)
        }

        logoutButton.setOnClickListener {
            Toast.makeText(context, "Logging out...", Toast.LENGTH_SHORT).show()
            performLogout()
        }

        return view
    }

    private fun performLogout() {
        val preferences = activity?.getSharedPreferences("MyPrefs", android.content.Context.MODE_PRIVATE)
        preferences?.edit()?.clear()?.apply()

        val intent = Intent(activity, TabLayout::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or
                    Intent.FLAG_ACTIVITY_NEW_TASK or
                    Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
        activity?.finish()
    }
}
