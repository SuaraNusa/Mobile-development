package com.example.suaranusa.ui.main

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.suaranusa.R
import com.example.suaranusa.SlidePageMenu
import com.example.suaranusa.databinding.ActivityMainBinding
import com.example.suaranusa.ui.auth.AuthTabActivity
import com.example.suaranusa.ui.profile.BlankFragment
import com.example.suaranusa.utils.SessionManager
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences
    private val viewModel: MainViewModel by viewModels{MainViewModelFactory(this)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sessionManager = SessionManager(this)
        val token = sessionManager.getToken()

        if (token != null){
            Log.d("Token", token)
        }

        viewModel.checkToken(
            onTokenValid = {
                Log.d("MainActivity", "onCreate: Token Valid")
            },
            onTokenInvalid = {
                Log.d("MainActivity", "onCreate: Token Invalid")
                Toast.makeText(this, "Please login first", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, AuthTabActivity::class.java))
                finish()
            }
        )

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
        navController.navigate(R.id.navigation_home)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_history,
                R.id.navigation_list,
                R.id.navigation_blank
            )
        )

        // hide nav top
        supportActionBar?.hide()

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