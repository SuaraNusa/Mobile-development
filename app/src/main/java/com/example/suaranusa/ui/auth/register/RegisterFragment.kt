package com.example.suaranusa.ui.auth.register

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.example.suaranusa.R
import com.example.suaranusa.model.vericationQuestion
import com.example.suaranusa.response.auth.DataItem
import com.example.suaranusa.ui.auth.AuthTabActivity


class RegisterFragment : Fragment(), LifecycleObserver {
    private val viewModel:RegisterViewModel by viewModels()
    private lateinit var spinner: Spinner
    private lateinit var rg_button: Button
    private lateinit var nameInput: EditText
    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var confirmPasswordInput: EditText
    private lateinit var answerInput: EditText


    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.auth_fragment_register, container, false)

        spinner = view.findViewById(R.id.question_spinner)
        rg_button = view.findViewById(R.id.register_button)
        nameInput = view.findViewById(R.id.username_input)
        emailInput = view.findViewById(R.id.email_input)
        passwordInput = view.findViewById(R.id.password_input)
        confirmPasswordInput = view.findViewById(R.id.confirm_password_input)
        answerInput = view.findViewById(R.id.answer_input)

        rg_button.setOnClickListener{register()}
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

    private fun getQuestionsId(): String{
        var questionsSelect:Array<String> = emptyArray()
        val questions = spinner.tag as List<DataItem>
        val selectedQuestion = questions[spinner.selectedItemPosition].question
        val selectedId = questions[spinner.selectedItemPosition].id
        questionsSelect.apply {
            questionsSelect += selectedQuestion
            questionsSelect += selectedId.toString()
        }

           return selectedId.toString()
    }

    @SuppressLint("CommitTransaction")
    private fun register() {
        val name = nameInput.text.toString()
        val email = emailInput.text.toString()
        val password = passwordInput.text.toString()
        val confirmPassword = confirmPasswordInput.text.toString()
        val answer = answerInput.text.toString()
        val verificationQuestion = listOf(vericationQuestion(getQuestionsId().toInt(), answer))
        viewModel.registerUser(name, email, password, confirmPassword, verificationQuestion).observe(viewLifecycleOwner) { response ->
            Log.d("TAG", "registerButtonDemo: $response")
            activity?.runOnUiThread {
                if (response.status == "success") {
                    Toast.makeText(requireContext(), "Register Success", Toast.LENGTH_SHORT).show()
                    Log.d("TAG", "registerButtonLOG: ${response.data}")

                    // value set to default
                    nameInput.text.clear()
                    emailInput.text.clear()
                    passwordInput.text.clear()
                    confirmPasswordInput.text.clear()
                    answerInput.text.clear()

                    (activity as? AuthTabActivity)?.getViewPager()?.currentItem = 1
                } else {
                    // Toast.makeText(requireContext(), "Register Failed: ${response.errors}", Toast.LENGTH_SHORT).show()
                    Toast.makeText(requireContext(), "Register Failed: ${errorType(response.errors.toString())}", Toast.LENGTH_SHORT).show()
                    Log.d("TAG", "registerButtonLOG: ${response.errors}")
                }
            }
        }

    }

    private fun errorType(error: String): String {
        return if (error.contains("must contain at least 5 character(s)")) {
            "Username must contain at least 5 character(s)"
        } else {
            error
        }
    }

}