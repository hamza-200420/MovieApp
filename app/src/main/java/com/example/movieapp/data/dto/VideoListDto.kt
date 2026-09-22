package com.example.movieapp.data.dto

import com.google.gson.annotations.SerializedName

data class VideoListDto(
    @SerializedName("results") val results: List<com.example.movieapp.data.dto.VideoDto>
)