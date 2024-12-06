package com.example.suaranusa.ui.auth.login

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.suaranusa.R
import com.example.suaranusa.ui.main.MainActivity
import com.example.suaranusa.utils.SessionManager

class LoginFragment : Fragment(), View.OnClickListener {

    private val viewModel: LoginViewModel by viewModels()
    private lateinit var emailInput : EditText
    private lateinit var passwordInput : EditText
    private lateinit var progressBar: ProgressBar
    private lateinit var  sm :SessionManager


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.auth_fragment_login, container, false)
        val login_button = view.findViewById<View>(R.id.login_button)
        progressBar = view.findViewById(R.id.progressBar)
        sm = SessionManager(requireContext())

        emailInput = view.findViewById(R.id.email_input)
        passwordInput = view.findViewById(R.id.password_input)

        viewModel.isLoading.observe(viewLifecycleOwner){isLoading ->
            if(isLoading) {
                progressBar.visibility = View.VISIBLE
                login_button.isEnabled = false
                login_button.isClickable = false
            }else{
                progressBar.visibility = View.GONE
                login_button.isEnabled = true
                login_button.isClickable = true
            }
        }

        login_button.setOnClickListener(this)
        return view
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.login_button -> {
                Log.d("Login", "onClick: Login")
                login()

                //Intent to Activity
//                startActivity(Intent(activity, MainActivity::class.java))
//                activity?.finish()
            }
        }
    }

    private fun login() {
        val email = emailInput.text.toString()
        val password = passwordInput.text.toString()
        viewModel.loginUser(email, password).observe(this){ reponse ->
            if(reponse.status == "success"){
                sm.setToken(reponse.data ?: "")
                showDialog(requireContext(), "Login Success", "Login Success")
                val intent = Intent(activity, MainActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }else{
                showDialog(requireContext(), "Login Failed", reponse.errors ?: "Login Failed")
            }
        }

    }

    private fun showDialog(context: Context, title: String, message: String){
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }

}