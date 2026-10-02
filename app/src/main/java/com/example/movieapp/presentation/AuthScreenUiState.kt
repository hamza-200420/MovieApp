package com.example.movieapp.presentation

data class AuthScreenUiState(
    val email: String = "",
    val password: String = "",
    val loginState: Boolean = false,
    val rememberMe: Boolean = false,
    val emailError: String = "",
    val passwordError: String = "",
    val authError: String = "",
    val isLoading: Boolean = false,
    val isAuthenticated: Boolean = false
)