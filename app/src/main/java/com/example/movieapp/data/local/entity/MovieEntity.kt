package com.example.movieapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val movieId: Int,
    val title: String,
    val posterPath: String?,
    val voteAverage: Double,
    val addedAt: Long = System.currentTimeMillis()
)