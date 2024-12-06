package com.example.suaranusa.response.auth

import com.google.gson.annotations.SerializedName

data class ResponseAuthQuestions(

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("errors")
	val errors: Boolean,

	@field:SerializedName("status")
	val status: String
)

data class DataItem(

	@field:SerializedName("question")
	val question: String,

	@field:SerializedName("id")
	val id: Int
)

data class Data(

	@field:SerializedName("data")
	val data: List<DataItem>
)
