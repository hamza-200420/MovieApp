package com.example.movieapp.data.dto

import com.google.gson.annotations.SerializedName

data class CountryDto(
    @SerializedName("iso_3166_1") val isoCode: String,
    @SerializedName("english_name") val englishName: String
)