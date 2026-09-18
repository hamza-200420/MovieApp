package com.example.movieapp.data.dto

import com.google.gson.annotations.SerializedName

data class ReviewPageDto(
    @SerializedName("results") val results: List<ReviewDto>,
    @SerializedName("total_results") val totalResults: Int
)
