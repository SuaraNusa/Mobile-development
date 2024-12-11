import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.suaranusa.repository.ProfileRepository
import com.example.suaranusa.response.profile.ResponseProfile
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class EditProfileViewModel(private val repository: ProfileRepository) : ViewModel() {

    /**
     * Update user profile.
     */
    fun editProfile(
        name: String,
        email: String,
        password: String,
        confirmPassword: String,
        profile: MultipartBody.Part
    ):LiveData<ResponseProfile> {
        val _responseProfile: MutableLiveData<ResponseProfile> = MutableLiveData()
        _responseProfile.value = ResponseProfile(null, "Loading", "loading")
        viewModelScope.launch {
            try {
                val response = repository.editProfile(name, email, password, confirmPassword, profile)
                _responseProfile.value = response
            } catch (e: Exception) {
                _responseProfile.value = ResponseProfile(
                    null,
                    e.message ?: "An unknown error occurred",
                    "error"
                )
            }
        }
        return _responseProfile
    }
}
