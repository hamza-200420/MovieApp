package com.example.movieapp.data.dto

import com.google.gson.annotations.SerializedName

data class CertificationDto(
    @SerializedName("certification") val certification: String,
    @SerializedName("type") val type: Int
)