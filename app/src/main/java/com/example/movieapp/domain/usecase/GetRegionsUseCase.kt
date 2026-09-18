package com.example.movieapp.domain.usecase

import com.example.movieapp.domain.model.Region
import com.example.movieapp.domain.repository.MovieRepository
import javax.inject.Inject

class GetRegionsUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(): List<Region> {
        return repository.getRegions()
    }
}