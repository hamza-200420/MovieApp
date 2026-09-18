package com.example.movieapp.data.dto

import com.google.gson.annotations.SerializedName

data class GenreListDto(
    @SerializedName("genres") val genres: List<GenreDto>
)