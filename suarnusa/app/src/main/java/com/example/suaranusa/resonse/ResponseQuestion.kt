package com.example.suaranusa.resonse

data class ResponseQuestion(
	val result: Result
)

data class DataItem(
	val question: String,
	val id: Int
)

data class Result(
	val data: List<DataItem>
)

