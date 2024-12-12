package com.example.suaranusa.ui.main

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.suaranusa.R
import com.example.suaranusa.SlidePageMenu
import com.example.suaranusa.databinding.ActivityMainBinding
import com.example.suaranusa.ui.auth.AuthTabActivity
import com.example.suaranusa.ui.home.HomeFragment
import com.example.suaranusa.ui.home.result.ResultFragment
import com.example.suaranusa.utils.SessionManager
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var navView: BottomNavigationView
    private lateinit var navController: NavController

    private val viewModel: MainViewModel by viewModels { MainViewModelFactory(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sessionManager = SessionManager(this)
        val token = sessionManager.getToken()

        if (token != null) {
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

        navView = binding.navView

        navController = findNavController(R.id.nav_host_fragment_activity_main)
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

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.result_fragment) {
                navView.visibility = View.GONE
            } else {
                navView.visibility = View.VISIBLE
            }
        }
    }

    private fun getCurrentFragment(): Fragment? {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as? NavHostFragment
        return navHostFragment?.childFragmentManager?.primaryNavigationFragment
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            val currentFragment = getCurrentFragment()
            if (currentFragment is ResultFragment) {
                navController.navigate(R.id.navigation_home)
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }
}