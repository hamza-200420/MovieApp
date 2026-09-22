package com.example.movieapp.data.dto


import com.google.gson.annotations.SerializedName

data class MoviePageDto(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val results: List<com.example.movieapp.data.dto.MovieDto>,
    @SerializedName("total_pages") val totalPages: Int
)