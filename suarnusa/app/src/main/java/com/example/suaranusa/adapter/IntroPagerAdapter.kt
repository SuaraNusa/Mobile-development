package com.example.suaranusa.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.suaranusa.intro.FirstPage
import com.example.suaranusa.intro.SecondPage

class IntroPagerAdapter(FragmentActivity: FragmentActivity): FragmentStateAdapter(FragmentActivity) {
    override fun getItemCount(): Int  = 2

    override fun createFragment(position: Int): Fragment {
        //binding button to change page

        return when(position){
            0->FirstPage()
            else -> SecondPage()
        }
    }
}