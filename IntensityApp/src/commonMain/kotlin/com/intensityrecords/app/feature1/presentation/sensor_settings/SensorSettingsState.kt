package com.intensityrecord.sensor.presentation.sensor_settings

data class SensorSettingsState(
    val padId: String = "",
    val displayName: String = "",
    val monitorOn: Boolean = false,
    val timeSlot1: Boolean = false,
    val timeSlot2: Boolean = false,
    val timeSlot3: Boolean = false,
    val timeSlot4: Boolean = false,
    val timeSlot5: Boolean = false,
    val alertTimeIdx: Int = 0,
    val alertTimeLabels: List<String> = emptyList(),
    val repeatAlarmUntilSeen: Boolean = false,
    val chartEnabled: Boolean = false,
    val calibrateEnabled: Boolean = false,
    val isLoading: Boolean = false,
    val isSaving: Boolean = false,
    val saveSuccess: Boolean = false,
    val error: String? = null,
    val timeslotLabels: List<String> = emptyList()
)
