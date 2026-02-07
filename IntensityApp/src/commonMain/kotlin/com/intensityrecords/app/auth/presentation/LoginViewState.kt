package com.intensityrecord.auth.presentation

data class LoginState(
    val username: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val isLoading: Boolean = false,
    val usernameError: String? = null,
    val passwordError: String? = null,
    val loginError: String? = null,
    val isLoginSuccess: Boolean = false,
    val showDemoLink: Boolean = true,
    val versionName: String = "1.0"
)
