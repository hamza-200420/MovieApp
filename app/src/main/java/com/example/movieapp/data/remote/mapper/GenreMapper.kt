package com.example.movieapp.data.remote.mapper

import com.example.movieapp.data.remote.dto.GenreDto
import com.example.movieapp.domain.model.Genre

fun GenreDto.toDomain(): Genre {
    return Genre(id = id, name = name)
}