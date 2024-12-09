package com.example.suaranusa.ui.home

import android.content.Context
import android.media.MediaRecorder
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.suaranusa.repository.PredictRepository
import com.example.suaranusa.response.predict.ResponsePredict
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.IOException

class HomeViewModel(private val repository: PredictRepository, private val context:Context) : ViewModel() {

    private lateinit var mediaRecorder: MediaRecorder
    private lateinit var audioFile:File
    private val handler = Handler(Looper.getMainLooper())

    private val _isRecording = MutableLiveData<Boolean>()
    val isRecording: LiveData<Boolean> get() = _isRecording

    private val _isError = MutableLiveData<String?>()
    val isError: LiveData<String?> get() = _isError

    private val _responsePredict = MutableLiveData<ResponsePredict>()
    val responsePredict: LiveData<ResponsePredict> get() = _responsePredict

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    fun fetchInstrument(file: MultipartBody.Part){
        viewModelScope.launch {
            try {
              val response = repository.predict(file)
                val fiveVideos = response.data?.videos?.take(5)
                val newVideos = response.copy(data = response.data?.copy(videos = fiveVideos))
                _responsePredict.value = newVideos
                Log.d("PRED", "fetchInstrument: ${_responsePredict.value}")

            }catch (e: Exception){
                _responsePredict.value = ResponsePredict(null, e.message, "error")
                _isError.value = "error"
                Log.d("PRED", "fetchInstrument: ${_responsePredict.value}")

            }
        }
    }

    fun startRecording(){
        val storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC)
        audioFile = File.createTempFile("audio", ".wav", storageDir)
        mediaRecorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.DEFAULT)
            setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT)
            setOutputFile(audioFile.absolutePath)
            try {
                _isRecording.value = true
                prepare()
                start()
            }catch (e: IOException){
                e.printStackTrace()
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
        handler.postDelayed({
            stopRecording()
        }, 10000)
    }

    private fun stopRecording(){
        mediaRecorder.stop()
        mediaRecorder.release()
        _isRecording.value = false

        Log.d("audio", "stopRecording: ${audioFile.absolutePath}")

        val file = RequestBody.create("audio/wav".toMediaTypeOrNull(), audioFile )
        val body = MultipartBody.Part.createFormData("voice", audioFile.name, file)

        fetchInstrument(body)
    }


}