package com.example.movieapp.data.repository

import com.example.movieapp.data.local.datastore.OnboardingPreferencesDataSource
import com.example.movieapp.domain.repository.OnboardingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OnboardingRepositoryImpl @Inject constructor(
    private val dataSource: OnboardingPreferencesDataSource
) : OnboardingRepository {

    override fun isOnboardingCompleted(): Flow<Boolean> {
        return dataSource.isOnboardingCompleted
    }

    override suspend fun setOnboardingCompleted(completed: Boolean) {
        dataSource.setOnboardingCompleted(completed)
    }
}