package com.example.movieapp.data.remote.mapper

import com.example.movieapp.data.remote.dto.CountryDto
import com.example.movieapp.domain.model.Region

fun CountryDto.toDomain(): Region {
    return Region(
        isoCode = isoCode,
        englishName = englishName
    )
}