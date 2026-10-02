package com.example.movieapp.presentation

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.domain.usecase.LoginUseCase
import com.example.movieapp.domain.usecase.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthScreenViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(AuthScreenUiState())
    val uiState: StateFlow<AuthScreenUiState> = _uiState.asStateFlow()

    fun toggleAuth() {
        _uiState.update { it.copy(loginState = !uiState.value.loginState) }
    }

    fun onEmailChange(value: String) {
        _uiState.update { it.copy(email = value.trim()) }
    }

    fun onPasswordChange(value: String) {
        _uiState.update { it.copy(password = value) }
    }

    fun onRememberMeChange(value: Boolean) {
        _uiState.update { it.copy(rememberMe = value) }
    }

    fun loginStateToggle() {
        _uiState.update { it.copy(loginState = !_uiState.value.loginState) }
    }

    private fun validateEmail(email: String): String {
        if (email.isEmpty()) return "Email can't be empty"
        else if (!Patterns.EMAIL_ADDRESS.matcher(email)
                .matches()
        ) return "Enter a valid email address"
        else return ""
    }

    private fun validatePassword(password: String): String {
        if (password.isEmpty()) return "Password can't be empty"
        else if (password.length < 6) return "Password must be at least 6 characters"
        else return ""
    }

    private fun validate() {
        _uiState.update {
            it.copy(
                emailError = validateEmail(it.email),
                passwordError = validatePassword(it.password)
            )
        }
    }

    fun authValidate() {
        if (uiState.value.isLoading) return
        validate()
        if (!uiState.value.emailError.isEmpty() ||
            !uiState.value.passwordError.isEmpty()
        ) return
        else {
            if (uiState.value.loginState) {
                viewModelScope.launch(Dispatchers.IO) {
                    _uiState.update { it.copy(isLoading = true, authError = "") }
                    try {
                        loginUseCase(
                            uiState.value.email,
                            uiState.value.password,
                            uiState.value.rememberMe
                        )
                        _uiState.update { it.copy(isLoading = false, isAuthenticated = true) }
                    } catch (e: Exception) {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                authError = e.localizedMessage ?: "Something went wrong"
                            )
                        }
                    }
                }
            } else {
                viewModelScope.launch(Dispatchers.IO) {
                    _uiState.update { it.copy(isLoading = true, authError = "") }
                    try {
                        signUpUseCase(
                            name = uiState.value.email.substringBefore("@"),
                            email = uiState.value.email,
                            password = uiState.value.password,
                            rememberMe = uiState.value.rememberMe
                        )
                        _uiState.update { it.copy(isLoading = false, isAuthenticated = true) }
                    } catch (e: Exception) {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                authError = e.localizedMessage ?: "Something went wrong"
                            )
                        }
                    }
                }
            }
        }
    }
}