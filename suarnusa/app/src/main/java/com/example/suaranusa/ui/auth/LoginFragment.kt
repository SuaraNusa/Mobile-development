package com.example.suaranusa.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.suaranusa.MainActivity
import com.example.suaranusa.R

class LoginFragment : Fragment(), View.OnClickListener {
    //


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.auth_fragment_login, container, false)
        val login_button = view.findViewById<View>(R.id.login_button)
        login_button.setOnClickListener(this)
        return view
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.login_button -> {
                //Intent to Activity
                startActivity(Intent(activity, MainActivity::class.java))
                activity?.finish()
            }
        }
    }


}