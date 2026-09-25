package com.example.movieapp.presentation

import androidx.lifecycle.ViewModel
import com.example.movieapp.domain.usecase.GetOnboardingStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val getOnboardingStatusUseCase: GetOnboardingStatusUseCase
) : ViewModel() {

    suspend fun isOnboardingCompleted(): Boolean {
        return getOnboardingStatusUseCase().first()
    }
}