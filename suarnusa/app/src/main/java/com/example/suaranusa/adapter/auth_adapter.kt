package com.example.suaranusa.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.suaranusa.ui.auth.LoginFragment
import com.example.suaranusa.ui.auth.RegisterFragment

class auth_adapter(
    fragmentManager: FragmentManager,
    lifeCycle:Lifecycle
): FragmentStateAdapter(fragmentManager, lifeCycle){

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> RegisterFragment()
            1 -> LoginFragment()
            else -> RegisterFragment()
        }
    }
}