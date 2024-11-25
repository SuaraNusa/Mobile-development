package com.example.suaranusa

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

import androidx.viewpager2.widget.ViewPager2
import com.example.suaranusa.adapter.auth_adapter
import com.example.suaranusa.databinding.AuthActivityTabLayoutBinding
import com.google.android.material.tabs.TabLayoutMediator

class TabLayout : AppCompatActivity() {

    private lateinit var binding : AuthActivityTabLayoutBinding
    private lateinit var tabLayout :  com.google.android.material.tabs.TabLayout
    private lateinit var viewPager : ViewPager2
    private lateinit var adapter : auth_adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.auth_activity_tab_layout)

        binding = AuthActivityTabLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        tabLayout = binding.tabLayout
        viewPager = binding.viewPager
        adapter = auth_adapter(supportFragmentManager, lifecycle)
        //tab layout
        Log.d("TabLayout", "onCreate: Tab Layout")
        tabLayout.addTab(tabLayout.newTab().setText("Register"), 0)
        tabLayout.addTab(tabLayout.newTab().setText("Login"), 1)

        supportActionBar?.hide()
        viewPager.adapter=adapter

        val tabTitles = arrayOf("Register", "Login")
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()


    }
}