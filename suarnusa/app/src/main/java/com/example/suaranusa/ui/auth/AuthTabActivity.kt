package com.example.suaranusa.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.suaranusa.R
import com.example.suaranusa.adapter.auth_adapter
import com.example.suaranusa.databinding.AuthActivityTabLayoutBinding
import com.example.suaranusa.ui.main.MainActivity
import com.google.android.material.tabs.TabLayoutMediator

class AuthTabActivity : AppCompatActivity() {

    private lateinit var binding : AuthActivityTabLayoutBinding
    private lateinit var tabLayout :  com.google.android.material.tabs.TabLayout
    private lateinit var viewPager : ViewPager2
    private lateinit var adapter : auth_adapter
    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.auth_activity_tab_layout)
        viewModel = AuthViewModel(this)
        binding = AuthActivityTabLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        tabLayout = binding.tabLayout
        viewPager = binding.viewPager
        adapter = auth_adapter(supportFragmentManager, lifecycle)
        //tab layout
        Log.d("AuthTabActivity", "onCreate: Tab Layout")
        tabLayout.addTab(tabLayout.newTab().setText("Register"), 0)
        tabLayout.addTab(tabLayout.newTab().setText("Login"), 1)

        supportActionBar?.hide()
        viewPager.adapter=adapter

        val tabTitles = arrayOf("Register", "Login")
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()

        viewModel.checkToken(
            onTokenValid = {
                Log.d("AuthTabActivity", "onCreate: Token Valid")
                showDialog("Already Logged In", "You are already logged in")
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            },
            onTokenInvalid = {
                Log.d("AuthTabActivity", "onCreate: Token Invalid")
            }
        )
    }

    private fun showDialog(tittle:String,message:String){
        val dialog = androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle(tittle)
            .setMessage(message)
            .setPositiveButton("OK"){dialog, which ->
                dialog.dismiss()
            }
        dialog.show()
    }
}