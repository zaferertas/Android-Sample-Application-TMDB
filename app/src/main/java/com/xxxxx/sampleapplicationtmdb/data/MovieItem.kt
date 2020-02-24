package com.xxxxx.sampleapplicationtmdb.data

import com.google.gson.annotations.SerializedName

data class MovieItem (
    @SerializedName("id")  val id:Int,
    @SerializedName("title")  val title: String?,
    @SerializedName("popularity")  val popularity: Float,
    @SerializedName("poster_path")  val posterPath: String?,
    @SerializedName("release_date")  val releaseDate: String?
)

