package com.example.movieapp.data.mapper

import com.example.movieapp.data.dto.CountryDto
import com.example.movieapp.domain.model.Region

fun CountryDto.toDomain(): Region {
    return Region(
        isoCode = isoCode,
        englishName = englishName
    )
}