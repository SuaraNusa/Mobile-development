import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.suaranusa.repository.AuthRepository
import com.example.suaranusa.repository.ProfileRepository
import com.example.suaranusa.response.auth.ResponseAuthLogin
import com.example.suaranusa.response.profile.ResponseProfile
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class EditProfileViewModel(private val repository: ProfileRepository) : ViewModel() {


    val authRepository = AuthRepository()

    var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun editProfile(
        name: RequestBody,
        email: RequestBody,
        password: RequestBody,
        confirmPassword: RequestBody,
        profile: MultipartBody.Part,
        emailString: String,
        passwordString: String,
    ):LiveData<ResponseProfile> {
        val _responseProfile: MutableLiveData<ResponseProfile> = MutableLiveData()
        _responseProfile.value = ResponseProfile(null, "Loading", "loading")
        viewModelScope.launch {
            try {
                val response = repository.editProfile(
                    name = name,
                    email = email,
                    password = password,
                    confirmPassword = confirmPassword,
                    profile = profile
                )
                _responseProfile.value = response
//                loginAgain(emailString, passwordString).observe()
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

    fun loginAgain(email: String, password:String):LiveData<ResponseAuthLogin>{
        _isLoading.value = true
        Log.d("Login", "onClick: Login")
        Log.d("Login", "email: $email, password: $password")
        val _mutableLiveLogin = MutableLiveData<ResponseAuthLogin>()
        viewModelScope.launch {
            val response = authRepository.loginUser(email, password)
            _mutableLiveLogin.value = response
        }
        _isLoading.value = false
        return _mutableLiveLogin
    }
}
