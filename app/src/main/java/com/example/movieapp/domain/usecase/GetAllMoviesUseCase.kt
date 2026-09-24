package com.example.movieapp.domain.usecase

import com.example.movieapp.domain.model.MovieDbModel
import com.example.movieapp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllMoviesUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke(): Flow<List<MovieDbModel>> {
        return repository.getAllMovies()
    }
}