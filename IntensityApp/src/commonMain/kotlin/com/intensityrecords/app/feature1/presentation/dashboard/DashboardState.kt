package com.intensityrecord.sensor.presentation.dashboard

import com.intensityrecord.sensor.domain.SensorData

data class DashboardState(
    val sensorList: List<SensorData> = emptyList(),
    val lastUpdateTime: String = "",
    val userName: String = "",
    val logoUrl: String? = null,
    val isLoading: Boolean = false,
    val connectionLost: Boolean = false,
    val isDemoMode: Boolean = false,
    val columns: Int = 3,
    val plugins: List<String> = emptyList(),
    val alertTimeColors: List<String> = emptyList(),
    val buttonStatusColors: Map<String, String> = emptyMap(),
    val showAlarmDialog: Boolean = false,
    val alarmSensorName: String = "",
    val alarmSensorPadId: String = "",
    val showDisconnectedDialog: Boolean = false,
    val showPinDialog: Boolean = false,
    val pendingSettingsPadId: String? = null
)
