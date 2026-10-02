package com.example.movieapp.presentation

import androidx.lifecycle.ViewModel
import com.example.movieapp.domain.usecase.CheckUserLoggedInUseCase
import com.example.movieapp.domain.usecase.GetOnboardingStatusUseCase
import com.example.movieapp.domain.usecase.GetRememberMeUseCase
import com.example.movieapp.domain.usecase.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val getOnboardingStatusUseCase: GetOnboardingStatusUseCase,
    private val checkUserLoggedInUseCase: CheckUserLoggedInUseCase,
    private val getRememberMeUseCase: GetRememberMeUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {

    suspend fun isOnboardingCompleted(): Boolean {
        return getOnboardingStatusUseCase().first()
    }

    suspend fun clearSessionIfNotRemembered() {
        if (checkUserLoggedInUseCase() && !getRememberMeUseCase().first()) {
            logoutUseCase()
        }
    }
}