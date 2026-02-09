package com.example.vecopay.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vecopay.core.util.Result
import com.example.vecopay.domain.model.User
import com.example.vecopay.domain.usecase.SignInUseCase
import com.example.vecopay.domain.usecase.SignUpUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class AuthState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val error: String? = null,
    val isSuccess: Boolean = false
)

class AuthViewModel(
    private val signInUseCase: SignInUseCase,
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {

    private val _authState = MutableStateFlow(AuthState())
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState(isLoading = true)

            when (val result = signInUseCase(email, password)) {
                is Result.Success -> {
                    _authState.value = AuthState(
                        user = result.data,
                        isSuccess = true
                    )
                }
                is Result.Error -> {
                    _authState.value = AuthState(
                        error = result.message ?: "Sign in failed"
                    )
                }
                is Result.Loading -> {
                    _authState.value = AuthState(isLoading = true)
                }
            }
        }
    }

    fun signUp(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState(isLoading = true)

            when (val result = signUpUseCase(email, password)) {
                is Result.Success -> {
                    _authState.value = AuthState(
                        user = result.data,
                        isSuccess = true
                    )
                }
                is Result.Error -> {
                    _authState.value = AuthState(
                        error = result.message ?: "Sign up failed"
                    )
                }
                is Result.Loading -> {
                    _authState.value = AuthState(isLoading = true)
                }
            }
        }
    }

    fun clearError() {
        _authState.value = _authState.value.copy(error = null)
    }
}