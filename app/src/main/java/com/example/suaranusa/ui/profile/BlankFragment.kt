package com.example.suaranusa.ui.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.suaranusa.R
import com.example.suaranusa.ui.auth.AuthTabActivity
import com.example.suaranusa.utils.SessionManager

class BlankFragment : Fragment() {
    private lateinit var sm: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sm = SessionManager(requireContext())
        val view = inflater.inflate(R.layout.fragment_blank, container, false)

        val myProfileSection: LinearLayout = view.findViewById(R.id.myProfileSection)
        val favoriteSection: LinearLayout = view.findViewById(R.id.favoriteSection)
        val logoutButton = view.findViewById<Button>(R.id.logoutButton)

        myProfileSection.setOnClickListener {
            val intent = Intent(requireContext(), ProfileActivity::class.java)
            startActivity(intent)
        }

        favoriteSection.setOnClickListener {
            val userId = sm.getUserId()
            if (userId != null) {
                val intent = Intent(requireContext(), FavActivity::class.java)
                intent.putExtra("USER_ID", userId)
                startActivity(intent)
            } else {
                Toast.makeText(context, "User ID not found", Toast.LENGTH_SHORT).show()
            }
        }

        logoutButton.setOnClickListener {
            Toast.makeText(context, "Logging out...", Toast.LENGTH_SHORT).show()
            performLogout()
        }

        return view
    }

    private fun performLogout() {
        sm.clearSession()
        Log.d("Logout", "Token: ${sm.getToken()}")
        val intent = Intent(activity, AuthTabActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or
                    Intent.FLAG_ACTIVITY_NEW_TASK or
                    Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
        activity?.finish()
    }
}
