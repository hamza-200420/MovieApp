package com.example.movieapp.domain.model

data class Review(
    val id: String,
    val authorName: String,
    val authorAvatarPath: String?,
    val content: String,
    val rating: Double?,
    val createdAt: String
)