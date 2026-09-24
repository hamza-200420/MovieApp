package com.example.movieapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class TvPageDto(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val results: List<TvDto>,
    @SerializedName("total_pages") val totalPages: Int
)