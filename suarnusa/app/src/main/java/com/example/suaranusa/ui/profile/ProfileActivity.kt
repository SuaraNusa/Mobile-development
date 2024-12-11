package com.example.suaranusa.ui.profile

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.suaranusa.R
import com.example.suaranusa.utils.SessionManager

class ProfileActivity : AppCompatActivity() {

    private lateinit var backButton: ImageView
    private lateinit var editButton: ImageView
    private lateinit var profileName: TextView
    private lateinit var profileEmail: TextView
    private lateinit var profilePicture: ImageView
    private lateinit var sm:SessionManager
    private lateinit var profileUrl: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()
        setContentView(R.layout.activity_profile)
        sm = SessionManager(this)
        val name = sm.getTokenValue("name")?.replace("\"", "")
        val nameUrl = name?.replace(" ", "")

        val email = sm.getTokenValue("email")?.replace("\"", "")

        backButton = findViewById(R.id.backButton)
        editButton = findViewById(R.id.editButton)
        profileName = findViewById(R.id.profileName)
        profileEmail = findViewById(R.id.profileEmail)
        profilePicture = findViewById(R.id.profileImageView)

        profileUrl = "https://ui-avatars.com/api/?name=$nameUrl.jpg"
        Glide.with(this)
            .load(profileUrl)
            .into(profilePicture)

        profileName.text = name
        profileEmail.text =email

        backButton.setOnClickListener {
            finish()
        }

        editButton.setOnClickListener {
            val intent = Intent(this, EditProfile::class.java).apply {
                putExtra(EditProfile.NAME, name)
                putExtra(EditProfile.EMAIL, email)
                putExtra(EditProfile.IMAGE_PROFILE, profileUrl)
            }
            startActivity(intent)
        }
    }
}
