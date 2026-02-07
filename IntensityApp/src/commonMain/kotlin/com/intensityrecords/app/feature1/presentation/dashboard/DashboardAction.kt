package com.intensityrecord.sensor.presentation.dashboard

sealed interface DashboardAction {
    data class OnSensorClick(val padId: String) : DashboardAction
    data class OnSensorLongClick(val padId: String) : DashboardAction
    data object OnSettingsClick : DashboardAction
    data object OnRefresh : DashboardAction
    data object DismissAlarmDialog : DashboardAction
    data object SnoozeAlarm : DashboardAction
    data object DismissDisconnectedDialog : DashboardAction
    data object DismissPinDialog : DashboardAction
    data class OnPinEntered(val pin: String) : DashboardAction
}
