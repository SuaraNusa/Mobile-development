import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.suaranusa.repository.ProfileRepository
import com.example.suaranusa.response.predict.ResponsePredict
import com.example.suaranusa.response.profile.ResponseProfile
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class BlankViewModel(private val repository: ProfileRepository) : ViewModel() {
    private val _responseProfile = MutableLiveData<ResponseProfile>()
    val responseProfile: LiveData<ResponseProfile> get() = _responseProfile

    private val _text = MutableLiveData<String>().apply {
        value = "This is Blank Fragment"
    }
    val text: LiveData<String> = _text

    private val _userName = MutableLiveData<String>().apply {
        value = "Guest"
    }
    val userName: LiveData<String> = _userName

    fun updateUserName(newUserName: String) {
        _userName.value = newUserName
    }

    fun editProfile(name: String, email: String, password: String, confirmPassword: String, profile: MultipartBody.Part){
        viewModelScope.launch {
            try {
                _responseProfile.value = repository.editProfile(name, email, password, confirmPassword, profile)
            }catch (e: Exception){
                _responseProfile.value = ResponseProfile(null, e.message, "error")
            }
        }
    }
}
