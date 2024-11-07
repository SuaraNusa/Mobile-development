package com.example.suaranusa

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
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

        binding.buttonNext.setOnClickListener {
            val current = binding.slideVP.currentItem
            if (current < 1) {
                binding.slideVP.currentItem = current + 1
            }
        }

       binding.slideVP.registerOnPageChangeCallback(object :ViewPager2.OnPageChangeCallback(){
           override fun onPageSelected(position: Int) {
               super.onPageSelected(position)
                if (position == 1){
                     binding.buttonNext.visibility = View.GONE
                }else{
                    binding.buttonNext.visibility = View.VISIBLE
                }
           }
       })

        val viewPager: ViewPager2 = binding.slideVP
        val dotsIndicator: DotsIndicator = binding.dotsIndicator
        viewPager.adapter = IntroPagerAdapter(this)

        dotsIndicator.attachTo(viewPager)
    }
}