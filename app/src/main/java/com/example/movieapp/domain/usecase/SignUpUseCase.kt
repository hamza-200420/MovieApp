package com.example.movieapp.domain.usecase

import com.example.movieapp.domain.model.User
import com.example.movieapp.domain.repository.AuthRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(
        name: String="ABC",
        email: String,
        password: String,
        rememberMe: Boolean
    ): User {
        val user = repository.signUp(name, email, password)
        repository.setRememberMe(rememberMe)
        return user
    }
}