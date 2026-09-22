package com.example.movieapp.data.dto

import com.google.gson.annotations.SerializedName

data class CreditsDto(
    @SerializedName("cast") val cast: List<com.example.movieapp.data.dto.CastMemberDto>
)