package com.example.movieapp.data.remote.mapper

import com.example.movieapp.data.remote.dto.MovieFirestoreDto
import com.example.movieapp.domain.model.MovieDbModel

fun MovieFirestoreDto.toDomain() = MovieDbModel(
    id = id,
    movieId = movieId,
    title = title,
    posterPath = posterPath,
    voteAverage = voteAverage,
    mediaType = mediaType
)

fun MovieDbModel.toFirestoreDto() = MovieFirestoreDto(
    id = id,
    movieId = movieId,
    title = title,
    posterPath = posterPath,
    voteAverage = voteAverage,
    mediaType = mediaType
)