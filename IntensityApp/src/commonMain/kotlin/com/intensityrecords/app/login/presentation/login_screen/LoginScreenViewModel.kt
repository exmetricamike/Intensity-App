package com.intensityrecords.app.login.presentation.login_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.intensityrecords.app.core.domain.onError
import com.intensityrecords.app.core.domain.onSuccess
import com.intensityrecords.app.login.domain.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginScreenViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state = _state.onStart {
        // Initialization logic if needed
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        _state.value
    )

    fun onAction(action: LoginAction) {
        when (action) {
            is LoginAction.OnUsernameChange -> {
                _state.update { it.copy(usernameText = action.username, errorMessage = null) }
            }

            is LoginAction.OnPasswordChange -> {
                _state.update { it.copy(passwordText = action.password, errorMessage = null) }
            }

            LoginAction.OnLoginClick -> performLogin()
            LoginAction.ResetError -> _state.update { it.copy(errorMessage = null) }
        }
    }

    private fun performLogin() {
        val currentState = _state.value
        if (currentState.usernameText.isBlank() || currentState.passwordText.isBlank()) {
            _state.update { it.copy(errorMessage = "Username and password cannot be empty") }
            return
        }

        _state.update { it.copy(isLoading = true, errorMessage = null) }

        viewModelScope.launch {
            val result = authRepository.login(
                username = currentState.usernameText,
                password = currentState.passwordText
            )

            result.onSuccess { authItem ->

                authRepository.saveSession(authItem.token, authItem.hotelId)

                // Typically you would save `authItem.token` to DataStore here
                _state.update { it.copy(isLoading = false, isLoginSuccessful = true) }
            }.onFailure { error ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = error.message ?: "An unknown error occurred"
                    )
                }
            }
        }
    }
}