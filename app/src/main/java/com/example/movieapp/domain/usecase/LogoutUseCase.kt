package com.example.movieapp.domain.usecase

import com.example.movieapp.domain.repository.AuthRepository
import com.example.movieapp.domain.repository.MovieRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke() {
        authRepository.logout()
        authRepository.setRememberMe(false)
    }
}