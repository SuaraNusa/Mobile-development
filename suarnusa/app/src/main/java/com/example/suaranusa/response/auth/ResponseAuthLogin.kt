package com.example.suaranusa.response.auth

import com.google.gson.annotations.SerializedName

data class ResponseAuthLogin(

	@field:SerializedName("data")
	val data: String? = null,

	@field:SerializedName("errors")
	val errors: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)
