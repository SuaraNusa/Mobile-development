package com.example.suaranusa

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.suaranusa.databinding.ActivityMainBinding
import com.example.suaranusa.ui.profile.BlankFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("isIntroShow", MODE_PRIVATE)
        val isIntroShow = sharedPreferences.getBoolean("isIntroShow", false)
        Log.d("Main Intro show", isIntroShow.toString())


        if (!isIntroShow) {
            startActivity(Intent(this, SlidePageMenu::class.java))
            finish()
            return
        }



        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        navController.navigate(R.id.navigation_home )

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_history, R.id.navigation_list, R.id.navigation_blank
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            val blankFragment = BlankFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, blankFragment)
                .commit()
        }
    }
}