package com.intensityrecords.app.core.data

sealed class AuthState {
    object Loading : AuthState()
    object LoggedOut : AuthState()
    data class LoggedIn(val token: String) : AuthState()
}
