package com.example.movieapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CreditsDto(
    @SerializedName("cast") val cast: List<CastMemberDto>
)