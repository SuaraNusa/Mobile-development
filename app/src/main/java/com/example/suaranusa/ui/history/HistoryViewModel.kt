package com.example.suaranusa.ui.history

import android.content.Context
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.suaranusa.repository.HistoryRepository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.auth0.jwt.interfaces.DecodedJWT
import com.example.suaranusa.model.HistoryItem
import com.example.suaranusa.utils.SessionManager
import com.example.suaranusa.utils.jwtDecoder
import kotlinx.coroutines.launch

class HistoryViewModel(private val repository: HistoryRepository, private val session: SessionManager) : ViewModel() {

    private val _historyItems = MutableLiveData<List<HistoryItem>>()
    private val _loading = MutableLiveData<Boolean>()

    val loading: LiveData<Boolean> get() = _loading
    val historyItems: LiveData<List<HistoryItem>> get() = _historyItems

    fun fetchHistoryItems() {
        _loading.value = true

        val id = getUserId()
        repository.getHistory(id).observeForever { items ->
            _historyItems.value = items
            _loading.value = false
        }
    }

    fun getUserId():Int{
        val token = session.getToken()
        val decode: DecodedJWT? = jwtDecoder.decode(token?:"")
        if(decode != null){
            val claims = decode.claims
            val idUser = claims["id"]
            return idUser.toString().toInt()
        }
        return 0
    }
}