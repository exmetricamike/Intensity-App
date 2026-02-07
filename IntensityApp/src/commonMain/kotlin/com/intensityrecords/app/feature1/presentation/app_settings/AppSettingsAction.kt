package com.intensityrecord.sensor.presentation.app_settings

sealed interface AppSettingsAction {
    data class ChangeColumns(val columns: Int) : AppSettingsAction
    data object ToggleSound : AppSettingsAction
    data object ToggleVibrate : AppSettingsAction
    data object Logout : AppSettingsAction
    data object OnBackClick : AppSettingsAction
}
