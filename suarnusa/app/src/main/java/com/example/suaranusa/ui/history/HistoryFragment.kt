package com.example.suaranusa.ui.history

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.suaranusa.adapter.HistoryAdapter
import com.example.suaranusa.R
import com.example.suaranusa.repository.HistoryRepository
import com.example.suaranusa.utils.SessionManager

class HistoryFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var historyAdapter: HistoryAdapter
    private lateinit var progressBar: ProgressBar

    private val viewModel: HistoryViewModel by viewModels {
        HistoryViewModelFactory(HistoryRepository(requireContext()), SessionManager(requireContext()))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_history, container, false)
        recyclerView = view.findViewById(R.id.recyclerViewHistory)
        progressBar = view.findViewById(R.id.progressBar)

        recyclerView.layoutManager = LinearLayoutManager(context)
        historyAdapter = HistoryAdapter(emptyList(), requireContext())
        recyclerView.adapter = historyAdapter


        viewModel.fetchHistoryItems()
        viewModel.historyItems.observe(viewLifecycleOwner) { historyItems ->
            historyAdapter.updateItems(historyItems?:emptyList())
        }

        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
            }
        }

        return view
    }
}