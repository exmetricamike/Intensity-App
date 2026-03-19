package com.intensityrecords.app.login.presentation.login_screen

sealed interface LoginAction {
    data class OnUsernameChange(val username: String) : LoginAction
    data class OnPasswordChange(val password: String) : LoginAction
    object OnLoginClick : LoginAction
    object ResetError : LoginAction
}