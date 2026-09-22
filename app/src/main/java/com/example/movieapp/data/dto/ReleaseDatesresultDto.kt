package com.example.movieapp.data.dto

import com.google.gson.annotations.SerializedName

data class ReleaseDatesResultDto(
    @SerializedName("results") val results: List<com.example.movieapp.data.dto.CountryReleaseDto>
)