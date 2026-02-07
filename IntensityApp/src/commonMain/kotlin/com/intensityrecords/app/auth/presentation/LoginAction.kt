package com.intensityrecord.auth.presentation

sealed class LoginEventAction {
    data class UserNameChanged(val username: String) : LoginEventAction()
    data class PasswordChanged(val password: String) : LoginEventAction()
    data object TogglePasswordVisibility : LoginEventAction()
    data object LoginClicked : LoginEventAction()
    data object DemoLinkClicked : LoginEventAction()
}

sealed class LoginNavigationEvents {
    data object Authenticated : LoginNavigationEvents()
    data object NavigateToDemo : LoginNavigationEvents()
}
