package com.intensityrecord.auth.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.intensityrecord.core.data.ZensiPreferences
import com.intensityrecord.core.domain.Result
import com.intensityrecord.sensor.domain.SensorRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val sensorRepository: SensorRepository,
    private val zensiPreferences: ZensiPreferences
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    private val _events = Channel<LoginNavigationEvents>()
    val events = _events.receiveAsFlow()

    init {
        // Pre-fill saved credentials if available
        val savedUsername = zensiPreferences.username
        val savedPassword = zensiPreferences.password
        if (!savedUsername.isNullOrBlank()) {
            _state.update { it.copy(username = savedUsername) }
        }
        if (!savedPassword.isNullOrBlank()) {
            _state.update { it.copy(password = savedPassword) }
        }
    }

    fun onEvent(event: LoginEventAction) {
        when (event) {
            is LoginEventAction.UserNameChanged -> {
                _state.update { it.copy(username = event.username, usernameError = null, loginError = null) }
            }
            is LoginEventAction.PasswordChanged -> {
                _state.update { it.copy(password = event.password, passwordError = null, loginError = null) }
            }
            is LoginEventAction.TogglePasswordVisibility -> {
                _state.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
            }
            is LoginEventAction.LoginClicked -> {
                if (validate()) {
                    login()
                }
            }
            is LoginEventAction.DemoLinkClicked -> {
                viewModelScope.launch {
                    _events.send(LoginNavigationEvents.NavigateToDemo)
                }
            }
        }
    }

    private fun validate(): Boolean {
        val currentState = _state.value
        if (currentState.username.isBlank()) {
            _state.update { it.copy(usernameError = "Username cannot be empty") }
            return false
        }
        if (currentState.password.isBlank()) {
            _state.update { it.copy(passwordError = "Password cannot be empty") }
            return false
        }
        return true
    }

    private fun login() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, loginError = null) }

            val username = _state.value.username
            val password = _state.value.password

            when (val result = sensorRepository.login(username, password)) {
                is Result.Success -> {
                    val token = result.data.token
                    zensiPreferences.token = token
                    zensiPreferences.storeCredentials(username, password)

                    // Also store token in the existing TokenStorage for the AuthPlugin
                    _state.update { it.copy(isLoading = false, isLoginSuccess = true) }
                    _events.send(LoginNavigationEvents.Authenticated)
                }
                is Result.Error -> {
                    val errorMessage = when (result.error) {
                        com.intensityrecord.core.domain.DataError.Remote.NO_INTERNET -> "No internet connection"
                        com.intensityrecord.core.domain.DataError.Remote.REQUEST_TIMEOUT -> "Request timed out"
                        com.intensityrecord.core.domain.DataError.Remote.SERVER -> "Server error"
                        else -> "Login failed. Check your credentials."
                    }
                    _state.update { it.copy(isLoading = false, loginError = errorMessage) }
                }
            }
        }
    }
}
