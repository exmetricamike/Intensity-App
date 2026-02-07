package com.intensityrecord.sensor.presentation.sensor_settings

sealed interface SensorSettingsAction {
    data object ToggleMonitor : SensorSettingsAction
    data class ToggleTimeSlot(val slot: Int) : SensorSettingsAction
    data class ChangeAlertTime(val index: Int) : SensorSettingsAction
    data object ToggleRepeatAlarm : SensorSettingsAction
    data class RenameDisplay(val name: String) : SensorSettingsAction
    data object OpenChart : SensorSettingsAction
    data object OpenCalibrate : SensorSettingsAction
    data object Save : SensorSettingsAction
    data object OnBackClick : SensorSettingsAction
}
