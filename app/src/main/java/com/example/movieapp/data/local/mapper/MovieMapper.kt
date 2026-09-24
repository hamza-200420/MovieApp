package com.example.movieapp.data.local.mapper

import com.example.movieapp.data.local.entity.MovieEntity
import com.example.movieapp.domain.model.MovieDbModel

fun MovieEntity.toDomain(): MovieDbModel {
    return MovieDbModel(
        id=id,
        movieId = movieId,
        title = title,
        posterPath = posterPath,
        voteAverage = voteAverage
    )
}

fun MovieDbModel.toEntity(): MovieEntity {
    return MovieEntity(
        id = id,
        movieId = movieId,
        title = title,
        posterPath = posterPath,
        voteAverage = voteAverage
    )
}