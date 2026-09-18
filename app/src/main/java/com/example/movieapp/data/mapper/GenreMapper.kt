package com.example.movieapp.data.mapper

import com.example.movieapp.data.dto.GenreDto
import com.example.movieapp.domain.model.Genre

fun GenreDto.toDomain(): Genre {
    return Genre(id = id, name = name)
}