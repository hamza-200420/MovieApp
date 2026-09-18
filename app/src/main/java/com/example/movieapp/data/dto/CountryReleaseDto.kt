package com.example.movieapp.data.dto

import com.google.gson.annotations.SerializedName

data class CountryReleaseDto(
    @SerializedName("iso_3166_1") val countryCode: String, // "US"
    @SerializedName("release_dates") val releaseDates: List<CertificationDto>
)