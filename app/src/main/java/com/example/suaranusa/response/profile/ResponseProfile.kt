package com.example.suaranusa.response.profile

import com.google.gson.annotations.SerializedName

data class ResponseProfile(
    @field:SerializedName("data")
    val data: DataProfile? = null,

    @field:SerializedName("errors")
    val errors: Any? = null,

    @field:SerializedName("status")
    val status: String? = null
)

data class DataProfile(
    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("email")
    val email: String? = null,

    @field:SerializedName("password")
    val password: String? = null,

    @field:SerializedName("confirmPassword")
    val confirmPassword: String? = null,

    @field:SerializedName("profile")
    val profile: String? = null
)