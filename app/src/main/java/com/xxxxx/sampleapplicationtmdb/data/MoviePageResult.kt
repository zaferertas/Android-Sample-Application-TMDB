package com.xxxxx.sampleapplicationtmdb.data

import com.google.gson.annotations.SerializedName


data class MoviePageResult (

    @SerializedName("page") val page: Int,
    @SerializedName("total_results") val totalResults: Int,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("results") val movieResult: ArrayList<MovieItem>?
)