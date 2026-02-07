package com.intensityrecord.sensor.presentation.app_settings

data class AppSettingsState(
    val numColumns: Int = 3,
    val soundEnabled: Boolean = true,
    val vibrateEnabled: Boolean = true
)
