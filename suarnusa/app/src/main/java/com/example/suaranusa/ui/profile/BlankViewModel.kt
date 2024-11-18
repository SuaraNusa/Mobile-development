import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BlankViewModel : ViewModel() {

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
}
