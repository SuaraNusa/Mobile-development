package com.example.suaranusa.resonse

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class ResponseAuthRegister(

	@field:SerializedName("password")
	val password: String,

	@field:SerializedName("verificationQuestions")
	val verificationQuestions: List<VerificationQuestionsItem>,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("confirmPassword")
	val confirmPassword: String,

	@field:SerializedName("email")
	val email: String
) : Parcelable

@Parcelize
data class VerificationQuestionsItem(

	@field:SerializedName("answer")
	val answer: String,

	@field:SerializedName("verificationQuestionId")
	val verificationQuestionId: Int
) : Parcelable
