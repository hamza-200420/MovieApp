package com.example.movieapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class VideoListDto(
    @SerializedName("results") val results: List<VideoDto>
)