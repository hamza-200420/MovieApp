package com.example.movieapp.data.repository

import com.example.movieapp.data.local.datastore.OnboardingPreferencesDataSource
import com.example.movieapp.data.remote.datasource.AuthRemoteDataSource
import com.example.movieapp.data.remote.mapper.toDomain
import com.example.movieapp.domain.model.User
import com.example.movieapp.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val remoteDataSource: AuthRemoteDataSource,
    private val prefs: OnboardingPreferencesDataSource
) : AuthRepository {

    override suspend fun signUp(name: String, email: String, password: String): User {
        return remoteDataSource.signUp(name, email, password).toDomain()
    }

    override suspend fun login(email: String, password: String): User {
        return remoteDataSource.login(email, password).toDomain()
    }

    override suspend fun logout() {
        remoteDataSource.logout()
    }

    override fun isUserLoggedIn(): Boolean {
        return remoteDataSource.isUserLoggedIn()
    }

    override suspend fun setRememberMe(remember: Boolean) {
        prefs.setRememberMe(remember)
    }

    override fun isRememberMeEnabled(): Flow<Boolean> {
        return prefs.rememberMe
    }
}