package com.example.suaranusa.ui.profile

import EditProfileViewModel
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.suaranusa.R
import com.example.suaranusa.repository.ProfileRepository
import com.example.suaranusa.utils.SessionManager
import com.example.suaranusa.utils.downloadFileToData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
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
    private lateinit var sm: SessionManager



    private lateinit var viewModel: EditProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_edit_profile)
        sm = SessionManager(this)
        profileImageView = findViewById(R.id.profileImageView)

        editName = findViewById(R.id.editName)
        editEmail = findViewById(R.id.editEmail)
        editPassword = findViewById(R.id.editPassword)
        editConfirmPassword = findViewById(R.id.editConfirmPassword)
        saveButton = findViewById(R.id.saveButton)
        backButton = findViewById(R.id.backButton)

        val repository = ProfileRepository(this)
        val username = intent.getStringExtra(NAME)
        val profileUrl = "https://ui-avatars.com/api/?name=$username.jpg"
      CoroutineScope(Dispatchers.IO).launch {
          val outputFile = downloadFileToData(this@EditProfile, profileUrl)
          if (outputFile != null) {
              Log.d("EditProfile", "Profile file downloaded to: ${outputFile.absolutePath}")
          } else {
              Log.d("EditProfile", "Profile file download failed")
          }
      }

        Glide.with(this)
            .load(profileUrl)
            .into(profileImageView)

        backButton.setOnClickListener {
            finish()
        }



        viewModel = ViewModelProvider(this, EditProfileViewModelFactory(repository)).get(EditProfileViewModel::class.java)

        saveButton.setOnClickListener {
            val name = editName.text.toString()
            val email = editEmail.text.toString()
            val password = editPassword.text.toString()
            val confirmPassword = editConfirmPassword.text.toString()


            val cacheDir = this.cacheDir
            val filePath = File(cacheDir, "profile.jpg")
            if (filePath.exists()) {
                Log.d("ProfileRepository", "Profile file path: ${filePath.absolutePath}")
            } else {
                Log.d("ProfileRepository", "Profile file does not exist at path: ${filePath.absolutePath}")
            }


            if (name.isEmpty() || email.isEmpty()|| password.isEmpty()|| confirmPassword.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
            else {
                val namePart = name.toRequestBody("text/plain".toMediaTypeOrNull())
                val emailPart = email.toRequestBody("text/plain".toMediaTypeOrNull())
                val passwordPart = password.toRequestBody("text/plain".toMediaTypeOrNull())
                val confirmPasswordPart = confirmPassword.toRequestBody("text/plain".toMediaTypeOrNull())

                val outputFile = File(this.filesDir, "profile.jpg")
                val requestFile = outputFile.asRequestBody("image/jpeg".toMediaTypeOrNull())
                val profilePart = MultipartBody.Part.createFormData("profile", outputFile.name, requestFile)



                viewModel.editProfile(
                  namePart,
                    emailPart,
                    passwordPart,
                    confirmPasswordPart,
                    profilePart,
                    email,
                    password
                ).observe(this, {
                    if (it.status == "success") {
                        Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show()
//                        sm.clearSession()

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