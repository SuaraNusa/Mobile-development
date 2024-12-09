package com.example.suaranusa.ui.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.suaranusa.R
import com.example.suaranusa.adapter.MusicalHeritageAdapter
import com.example.suaranusa.adapter.MusicalItem
import com.example.suaranusa.repository.InstrumentRepository
import com.example.suaranusa.response.instrument.DataItem
import com.example.suaranusa.ui.musicalheritage.MusicalHeritageViewModel


class MusicalHeritageFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var musicalAdapter: MusicalHeritageAdapter
    private lateinit var progressBar: ProgressBar

    private val viewModel :MusicalHeritageViewModel by viewModels{
        MusicalHeritageViewModelFactory(InstrumentRepository(requireContext()))
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_musical_heritage, container, false)
        Log.d("MusicalHeritageFragment", "onCreateView: ")
        recyclerView = view.findViewById(R.id.recyclerViewMusicalHeritage)
        progressBar = view.findViewById(R.id.progressBar)

        recyclerView.layoutManager = LinearLayoutManager(context)
        musicalAdapter = MusicalHeritageAdapter(emptyList(), requireContext())
        recyclerView.adapter = musicalAdapter

        viewModel.fetchInstruments()
        viewModel.loading.observe(viewLifecycleOwner){ isLoading ->
            if(isLoading){
                progressBar.visibility = View.VISIBLE
            }else{
                progressBar.visibility = View.GONE
            }

        }
        viewModel.instruments.observe(viewLifecycleOwner,  { response->
            response.data.let { dataItem ->
                val musicalItems = dataItem?.map{dataItem->
                    Log.d("MusicalHeritageFragment", "DataItems: ${dataItem}")
                    Log.d("MusicalHeritageFragment", "DataItems: $dataItem")

                    val imagePath = listOf(0, 1, 2, 3).mapNotNull { index ->
                        dataItem?.instrumentResources?.getOrNull(index)?.imagePath
                    }.firstOrNull() ?: ""
                    MusicalItem(
                        dataItem?.name?:"",
                        imagePath
                    )
                }
                musicalAdapter.updateItems(musicalItems?: emptyList())
            }
        } )

        return view
    }


}