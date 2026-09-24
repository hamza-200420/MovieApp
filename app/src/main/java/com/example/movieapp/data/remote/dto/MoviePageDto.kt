package com.example.movieapp.data.remote.dto


import com.google.gson.annotations.SerializedName

data class MoviePageDto(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val results: List<MovieDto>,
    @SerializedName("total_pages") val totalPages: Int
)