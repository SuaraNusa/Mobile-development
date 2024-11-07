package com.example.suaranusa

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.suaranusa.adapter.IntroPagerAdapter
import com.example.suaranusa.databinding.ActivitySlideBinding
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator

class SlidePageMenu : AppCompatActivity() {
    private lateinit var binding:ActivitySlideBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySlideBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewPager: ViewPager2 = binding.slideVP
        val dotsIndicator: DotsIndicator = binding.dotsIndicator
        viewPager.adapter = IntroPagerAdapter(this)
        dotsIndicator.attachTo(viewPager)
    }
}