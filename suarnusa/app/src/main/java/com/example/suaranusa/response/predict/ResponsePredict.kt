package com.example.suaranusa.response.predict

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResponsePredict(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("errors")
	val errors: String? = null,

	@field:SerializedName("status")
	val status: String? = null
):Parcelable

@Parcelize
data class Data(

	@field:SerializedName("score")
	val score: String? = null,

	@field:SerializedName("videos")
	val videos: List<VideosItem?>? = null,

	@field:SerializedName("songName")
	val songName: String

):Parcelable

@Parcelize
data class Ratings(

	@field:SerializedName("dislikes")
	val dislikes: Int? = null,

	@field:SerializedName("likes")
	val likes: Int? = null
):Parcelable

@Parcelize
data class Thumbnail(

	@field:SerializedName("width")
	val width: Int? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("url")
	val url: String? = null,

	@field:SerializedName("height")
	val height: Int? = null
):Parcelable

@Parcelize
data class VideosItem(

	@field:SerializedName("shorts_url")
	val shortsUrl: String? = null,

	@field:SerializedName("thumbnail")
	val thumbnail: Thumbnail? = null,

	@field:SerializedName("private")
	val jsonMemberPrivate: Boolean? = null,

	@field:SerializedName("nsfw")
	val nsfw: Boolean? = null,


	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("duration_formatted")
	val durationFormatted: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("url")
	val url: String? = null,

	@field:SerializedName("duration")
	val duration: Int? = null,

	@field:SerializedName("unlisted")
	val unlisted: Boolean? = null,

	@field:SerializedName("ratings")
	val ratings: Ratings? = null,

	@field:SerializedName("uploadedAt")
	val uploadedAt: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("shorts")
	val shorts: Boolean? = null,

	@field:SerializedName("views")
	val views: Int? = null,

	@field:SerializedName("live")
	val live: Boolean? = null
):Parcelable

