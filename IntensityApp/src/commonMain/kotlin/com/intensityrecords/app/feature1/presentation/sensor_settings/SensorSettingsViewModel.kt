package com.intensityrecord.sensor.presentation.sensor_settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.intensityrecord.core.data.ZensiPreferences
import com.intensityrecord.core.domain.Result
import com.intensityrecord.sensor.domain.SensorRepository
import com.intensityrecord.sensor.domain.SensorSettings
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed interface SensorSettingsEvent {
    data object SaveSuccess : SensorSettingsEvent
    data class NavigateToChart(val sensorName: String) : SensorSettingsEvent
    data class NavigateToCalibrate(val padId: String) : SensorSettingsEvent
}

class SensorSettingsViewModel(
    private val padId: String,
    private val sensorRepository: SensorRepository,
    private val zensiPreferences: ZensiPreferences
) : ViewModel() {

    private val _state = MutableStateFlow(SensorSettingsState(padId = padId))
    val state = _state.asStateFlow()

    private val _events = Channel<SensorSettingsEvent>()
    val events = _events.receiveAsFlow()

    init {
        loadSensorData()
    }

    fun onAction(action: SensorSettingsAction) {
        when (action) {
            is SensorSettingsAction.ToggleMonitor -> {
                _state.update { it.copy(monitorOn = !it.monitorOn) }
            }
            is SensorSettingsAction.ToggleTimeSlot -> {
                _state.update {
                    when (action.slot) {
                        1 -> it.copy(timeSlot1 = !it.timeSlot1)
                        2 -> it.copy(timeSlot2 = !it.timeSlot2)
                        3 -> it.copy(timeSlot3 = !it.timeSlot3)
                        4 -> it.copy(timeSlot4 = !it.timeSlot4)
                        5 -> it.copy(timeSlot5 = !it.timeSlot5)
                        else -> it
                    }
                }
            }
            is SensorSettingsAction.ChangeAlertTime -> {
                _state.update { it.copy(alertTimeIdx = action.index) }
            }
            is SensorSettingsAction.ToggleRepeatAlarm -> {
                _state.update { it.copy(repeatAlarmUntilSeen = !it.repeatAlarmUntilSeen) }
            }
            is SensorSettingsAction.RenameDisplay -> {
                _state.update { it.copy(displayName = action.name) }
            }
            is SensorSettingsAction.OpenChart -> {
                viewModelScope.launch {
                    _events.send(SensorSettingsEvent.NavigateToChart(_state.value.padId))
                }
            }
            is SensorSettingsAction.OpenCalibrate -> {
                viewModelScope.launch {
                    _events.send(SensorSettingsEvent.NavigateToCalibrate(_state.value.padId))
                }
            }
            is SensorSettingsAction.Save -> saveSettings()
            is SensorSettingsAction.OnBackClick -> { /* handled by navigation */ }
        }
    }

    private fun loadSensorData() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            when (val result = sensorRepository.getStatus(zensiPreferences.deviceId)) {
                is Result.Success -> {
                    val sensor = result.data.flatSensorList().find { it.padId == padId }
                    val plugins = result.data.plugins
                    val timeslots = zensiPreferences.timeslots
                    val timeslotLabels = timeslots.map { "${it.firstOrNull() ?: ""} - ${it.lastOrNull() ?: ""}" }

                    if (sensor != null) {
                        _state.update {
                            it.copy(
                                displayName = sensor.displayName,
                                monitorOn = sensor.monitorOn,
                                timeSlot1 = sensor.timeSlot1,
                                timeSlot2 = sensor.timeSlot2,
                                timeSlot3 = sensor.timeSlot3,
                                timeSlot4 = sensor.timeSlot4,
                                timeSlot5 = sensor.timeSlot5,
                                alertTimeIdx = sensor.alertTimeIdx,
                                alertTimeLabels = sensor.alertTimeLabels,
                                repeatAlarmUntilSeen = sensor.repeatAlarmUntilSeen,
                                chartEnabled = plugins.contains("allow_inapp_chart"),
                                calibrateEnabled = true,
                                isLoading = false,
                                timeslotLabels = timeslotLabels
                            )
                        }
                    } else {
                        _state.update { it.copy(isLoading = false, error = "Sensor not found") }
                    }
                }
                is Result.Error -> {
                    _state.update { it.copy(isLoading = false, error = "Failed to load sensor data") }
                }
            }
        }
    }

    private fun saveSettings() {
        viewModelScope.launch {
            _state.update { it.copy(isSaving = true) }
            val currentState = _state.value

            val settings = SensorSettings(
                padId = currentState.padId,
                displayName = currentState.displayName,
                isMonitoringActive = currentState.monitorOn,
                timeSlot1On = currentState.timeSlot1,
                timeSlot2On = currentState.timeSlot2,
                timeSlot3On = currentState.timeSlot3,
                timeSlot4On = currentState.timeSlot4,
                timeSlot5On = currentState.timeSlot5,
                alertTimeIdx = currentState.alertTimeIdx,
                repeatAlarmUntilSeen = currentState.repeatAlarmUntilSeen,
                deviceId = zensiPreferences.deviceId
            )

            when (sensorRepository.changeSensorSettings(settings)) {
                is Result.Success -> {
                    _state.update { it.copy(isSaving = false, saveSuccess = true) }
                    _events.send(SensorSettingsEvent.SaveSuccess)
                }
                is Result.Error -> {
                    _state.update { it.copy(isSaving = false, error = "Failed to save settings") }
                }
            }
        }
    }
}
