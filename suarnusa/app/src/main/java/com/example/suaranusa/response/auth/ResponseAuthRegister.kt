package com.example.suaranusa.response.auth

import com.google.gson.annotations.SerializedName

data class ResponseAuthRegister(

	@field:SerializedName("data")
	val data: String? = null,

	@field:SerializedName("errors")
	val errors: Any? = null,

	@field:SerializedName("status")
	val status: String? = null
)
