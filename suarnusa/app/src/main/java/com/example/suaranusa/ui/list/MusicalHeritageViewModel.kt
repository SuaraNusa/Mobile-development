package com.example.suaranusa.ui.musicalheritage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.suaranusa.R

data class Instrument(val name: String, val imageResId: Int, val description: String)

//class MusicalHeritageViewModel : ViewModel() {
//
//    private val _instruments = MutableLiveData<List<Instrument>>().apply {
//        value = listOf(
//            Instrument("GAMELAN", R.drawable.gamelan, "Asal daerah: Jawa, Lorem ipsum..."),
//    }
//
//    val instruments: LiveData<List<Instrument>> = _instruments
//}
