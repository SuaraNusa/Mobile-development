package com.example.suaranusa.ui.profile

import EditProfileViewModel
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.suaranusa.R
import com.example.suaranusa.repository.ProfileRepository
import com.example.suaranusa.utils.DownloadImageTask
import com.example.suaranusa.utils.downloadImage
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class EditProfile : AppCompatActivity() {
    private lateinit var profileImageView: ImageView
//    private lateinit var uploadImageButton: Button
    private lateinit var editName: EditText
    private lateinit var editEmail: EditText
    private lateinit var saveButton: Button
    private lateinit var backButton: ImageView
    private lateinit var editPassword: EditText
    private lateinit var editConfirmPassword: EditText

    private val pickImageRequestCode = 1001
    private var selectedImageUri: Uri? = null


    private lateinit var viewModel: EditProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_edit_profile)
        
        profileImageView = findViewById(R.id.profileImageView)

        editName = findViewById(R.id.editName)
        editEmail = findViewById(R.id.editEmail)
        editPassword = findViewById(R.id.editPassword)
        editConfirmPassword = findViewById(R.id.editConfirmPassword)
        saveButton = findViewById(R.id.saveButton)
        backButton = findViewById(R.id.backButton)

        val profileUrl = intent.getStringExtra(IMAGE_PROFILE)

        val outputFile = File(cacheDir, "profile.jpg")
        DownloadImageTask{success ->
            if(!success){
                Toast.makeText(this, "Failed to get Image", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, "Image downloaded", Toast.LENGTH_SHORT).show()
            }
        }.execute(profileUrl, outputFile.absolutePath)
        Glide.with(this)
            .load(profileUrl)
            .into(profileImageView)




        backButton.setOnClickListener {
            finish()
        }


        val repository = ProfileRepository(this)
        viewModel = ViewModelProvider(this, EditProfileViewModelFactory(repository)).get(EditProfileViewModel::class.java)

        saveButton.setOnClickListener {
            val name = editName.text.toString()
            val email = editEmail.text.toString()
            val password = editPassword.text.toString()
            val confirmPassword = editConfirmPassword.text.toString()

            if (name.isEmpty() || email.isEmpty()|| password.isEmpty()|| confirmPassword.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            } else {
                val file = File(cacheDir, "profile.jpg")
                val requestFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
                val profilePart = MultipartBody.Part.createFormData("profile", file.name, requestFile)
                viewModel.editProfile(
                    name = name,
                    email = email,
                    password = password,
                    confirmPassword = confirmPassword,
                    profile = profilePart).observe(this, {
                    if (it.status == "success") {
                        Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show()
//                        finish()
                    } else {
                        Toast.makeText(this, "${it.errors}", Toast.LENGTH_SHORT).show()
                        Log.d("EditProfile", "Error: ${it.errors}")
                    }
                })
            }
        }
    }

//    @Deprecated("Deprecated in Java")
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == pickImageRequestCode && resultCode == Activity.RESULT_OK) {
//            selectedImageUri = data?.data
//            profileImageView.setImageURI(selectedImageUri)
//        }
//    }

    companion object{
         const val IMAGE_PROFILE = "profile"
         const val NAME = "name"
         const val EMAIL = "email"
    }

}