package com.example.suaranusa.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.suaranusa.R
import com.example.suaranusa.ui.musical.MusicalHeritageAdapter
import com.example.suaranusa.ui.musical.MusicalItem


class MusicalHeritageFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var musicalAdapter: MusicalHeritageAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_musical_heritage, container, false)

        recyclerView = view.findViewById(R.id.recyclerViewMusicalHeritage)

        recyclerView.layoutManager = GridLayoutManager(context, 2)

        musicalAdapter = MusicalHeritageAdapter(getMusicalItems())
        recyclerView.adapter = musicalAdapter

        return view
    }


    private fun getMusicalItems(): List<MusicalItem> {
        return listOf(
            MusicalItem("Gamelan", R.drawable.gamelan),
            MusicalItem("Gamelan", R.drawable.gamelan),
            MusicalItem("Gamelan", R.drawable.gamelan),
            MusicalItem("Gamelan", R.drawable.gamelan),
            MusicalItem("Gamelan", R.drawable.gamelan),
            MusicalItem("Gamelan", R.drawable.gamelan),

        )
    }
}