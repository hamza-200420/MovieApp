package com.example.movieapp.data.dto

import com.google.gson.annotations.SerializedName

data class AuthorDetailsDto(
    @SerializedName("name") val name: String,
    @SerializedName("username") val username: String,
    @SerializedName("avatar_path") val avatarPath: String?,
    @SerializedName("rating") val rating: Double?
)