package com.example.movieapp.domain.usecase

import com.example.movieapp.domain.model.Genre
import com.example.movieapp.domain.repository.MovieRepository
import javax.inject.Inject

class GetGenresUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(): List<Genre> {
       return repository.getGenres()

    }
}