package com.example.movieapp.domain.usecase

import com.example.movieapp.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRememberMeUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(): Flow<Boolean> {
        return repository.isRememberMeEnabled()
    }
}