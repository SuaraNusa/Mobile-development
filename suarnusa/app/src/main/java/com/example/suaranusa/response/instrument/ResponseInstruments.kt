package com.example.suaranusa.response.instrument

import com.google.gson.annotations.SerializedName

data class ResponseInstruments(

	@field:SerializedName("data")
	val data: List<DataItem?>? = null,

	@field:SerializedName("errors")
	val errors: Any? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class DataItem(

	@field:SerializedName("instrumentCategory")
	val instrumentCategory: String? = null,

	@field:SerializedName("InstrumentResources")
	val instrumentResources: List<InstrumentResourcesItem?>? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("originRegional")
	val originRegional: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)

data class InstrumentResourcesItem(

	@field:SerializedName("videoUrl")
	val videoUrl: String? = null,

	@field:SerializedName("imagePath")
	val imagePath: String? = null,

	@field:SerializedName("instrumentId")
	val instrumentId: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("audioPath")
	val audioPath: Any? = null
)
