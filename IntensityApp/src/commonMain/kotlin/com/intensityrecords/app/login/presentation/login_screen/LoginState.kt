package com.intensityrecords.app.login.presentation.login_screen

data class LoginState(
    val usernameText: String = "",
    val passwordText: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isLoginSuccessful: Boolean = false
)