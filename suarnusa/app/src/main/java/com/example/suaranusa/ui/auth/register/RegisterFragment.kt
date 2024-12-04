package com.example.suaranusa.ui.auth.register

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.example.suaranusa.R
import com.example.suaranusa.response.auth.DataItem


class RegisterFragment : Fragment(), LifecycleObserver {

    private val viewModel:RegisterViewModel by viewModels()
    private lateinit var spinner: Spinner
    private lateinit var rg_button: Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.auth_fragment_register, container, false)

        spinner = view.findViewById(R.id.question_spinner)
        rg_button = view.findViewById(R.id.register_button)
        rg_button.setOnClickListener{registerButtonDemo()}
        lifecycle.addObserver(this)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSpinner()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun setupSpinner(){
        viewModel.questions.observe(viewLifecycleOwner){ response->
            Log.d("Tag", "SetupSpinner: $response")
            val questions = response.data.data
            val questionsTexts = questions.map { it.question }
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, questionsTexts)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
            spinner.tag = questions

        }
    }

    private fun registerButtonDemo(){
        var questionsSelect:Array<String> = emptyArray()
        val questions = spinner.tag as List<DataItem>
        val selectedQuestion = questions[spinner.selectedItemPosition].question
        val selectedId = questions[spinner.selectedItemPosition].id
        questionsSelect.apply {
            questionsSelect += selectedQuestion
            questionsSelect += selectedId.toString()
        }
        Log.d("TAG", "registerButtonDemo: $selectedQuestion")
        Toast.makeText(requireContext(), "Selected question: ${selectedQuestion}", Toast.LENGTH_SHORT).show()
    }


}