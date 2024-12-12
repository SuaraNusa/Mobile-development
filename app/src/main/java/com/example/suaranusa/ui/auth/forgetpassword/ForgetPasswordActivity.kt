package com.example.suaranusa.ui.auth.forgetpassword

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.suaranusa.R
import com.example.suaranusa.databinding.ActivityForgetPasswordBinding

class ForgetPasswordActivity : AppCompatActivity() {
    private val viewModel: ForgetPasswordViewModel by viewModels()
    private lateinit var binding: ActivityForgetPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //disabled action bar
        supportActionBar?.hide()

        binding.resetPassword.setOnClickListener {
            val email = binding.emailInput.text.toString()
            val password = binding.passwordInput.text.toString()
            val confirmPassword = binding.confirmPasswordInput.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {

                if(password != confirmPassword){
                    Toast.makeText(this, "Password and Confirm Password must be the same", Toast.LENGTH_SHORT).show()
                }else{
                    viewModel.resetPassword(email = email, password=password, confirmPassword = confirmPassword).observe(this){response ->
                        if(response.status == "success"){
                            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                            finish()
                        }else{
                            Toast.makeText(this, "${response.errors}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }



    }
}