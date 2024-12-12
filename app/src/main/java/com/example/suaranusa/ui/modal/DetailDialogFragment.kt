package com.example.suaranusa.ui.modal

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.suaranusa.adapter.VideoAdapter
import com.example.suaranusa.databinding.FragmentDetailPredictedBinding
import com.example.suaranusa.response.predict.ResponsePredict


class DetailDialogFragment : DialogFragment() {

    private var _binding: FragmentDetailPredictedBinding? = null
    private val binding get() = _binding!!

    @SuppressLint("NewApi")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = FragmentDetailPredictedBinding.inflate(layoutInflater)
        val dialog = Dialog(requireContext())
        dialog.setContentView(binding.root)

        val historyItem = arguments?.getParcelable("historyItem", ResponsePredict::class.java)

        val data = historyItem?.data
        val videos = data?.videos

        binding.backButton.setOnClickListener {
            dialog.dismiss()
        }

        Log.d((videos?.size).toString(), "videos size")

        binding.listVideoModal.layoutManager = GridLayoutManager(context, 1)
        binding.listVideoModal.adapter = videos?.let { VideoAdapter(it) }

        return dialog
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    companion object {
        fun newInstance(historyItem: ResponsePredict): DetailDialogFragment {
            val args = Bundle()
            args.putParcelable("historyItem", historyItem as Parcelable)
            val fragment = DetailDialogFragment()
            fragment.arguments = args
            return fragment
        }
    }
}