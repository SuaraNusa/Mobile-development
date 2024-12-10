import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.suaranusa.repository.ProfileRepository
import com.example.suaranusa.response.profile.ResponseProfile
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class BlankViewModel(private val repository: ProfileRepository) : ViewModel() {

    private val _responseProfile = MutableLiveData<ResponseProfile>()
    val responseProfile: LiveData<ResponseProfile> get() = _responseProfile

    /**
     * Update user profile.
     */
    fun editProfile(
        name: String,
        email: String,
        password: String,
        confirmPassword: String,
        profile: MultipartBody.Part
    ) {
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
    }
}
