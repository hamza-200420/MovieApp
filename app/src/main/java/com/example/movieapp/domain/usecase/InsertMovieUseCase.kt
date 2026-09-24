package com.example.movieapp.domain.usecase
import com.example.movieapp.domain.model.MovieDbModel
import com.example.movieapp.domain.repository.MovieRepository
import javax.inject.Inject

class InsertMovieUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(movie: MovieDbModel) {
        repository.insertMovie(movie)
    }
}