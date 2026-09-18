package com.example.movieapp.domain.usecase

import com.example.movieapp.domain.model.MoviePage
import com.example.movieapp.domain.repository.MovieRepository
import javax.inject.Inject

class GetNowPlayingUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(page: Int = 1): MoviePage {
       return repository.getNowPlaying(page)
    }
}