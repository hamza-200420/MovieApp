package com.example.movieapp.domain.repository

import com.example.movieapp.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun signUp(name: String, email: String, password: String): User
    suspend fun login(email: String, password: String): User
    suspend fun logout()
    fun isUserLoggedIn(): Boolean
    suspend fun setRememberMe(remember: Boolean)
    fun isRememberMeEnabled(): Flow<Boolean>
}