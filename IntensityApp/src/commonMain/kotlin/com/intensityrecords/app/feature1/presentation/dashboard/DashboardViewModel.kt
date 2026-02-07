package com.intensityrecord.sensor.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.intensityrecord.core.data.ZensiPreferences
import com.intensityrecord.core.domain.Result
import com.intensityrecord.sensor.domain.AppParameters
import com.intensityrecord.sensor.domain.SensorRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed interface DashboardEvent {
    data class NavigateToSensorSettings(val padId: String) : DashboardEvent
    data object NavigateToAppSettings : DashboardEvent
}

class DashboardViewModel(
    private val sensorRepository: SensorRepository,
    private val zensiPreferences: ZensiPreferences
) : ViewModel() {

    private val _state = MutableStateFlow(DashboardState())
    val state = _state.asStateFlow()

    private val _events = Channel<DashboardEvent>()
    val events = _events.receiveAsFlow()

    private var pollingJob: Job? = null
    private var pollingIntervalSecs = AppParameters.DEFAULT_APP_POLLING_INTERVAL_SECS
    private var storedPin: String? = null

    init {
        _state.update {
            it.copy(
                columns = zensiPreferences.numColumns,
                isDemoMode = zensiPreferences.isDemoMode,
                logoUrl = zensiPreferences.logoUrl
            )
        }
        fetchLogo()
        fetchTimeslots()
        fetchPin()
        startPolling()
    }

    fun onAction(action: DashboardAction) {
        when (action) {
            is DashboardAction.OnSensorClick -> {
                val sensor = _state.value.sensorList.find { it.padId == action.padId }
                // Toggle showTime is a local UI state - handled via sensor card
            }
            is DashboardAction.OnSensorLongClick -> {
                val isPinRequired = _state.value.plugins.contains("sensor_settings_blocked_by_pin")
                if (isPinRequired && !storedPin.isNullOrBlank()) {
                    _state.update {
                        it.copy(showPinDialog = true, pendingSettingsPadId = action.padId)
                    }
                } else {
                    viewModelScope.launch {
                        _events.send(DashboardEvent.NavigateToSensorSettings(action.padId))
                    }
                }
            }
            is DashboardAction.OnSettingsClick -> {
                viewModelScope.launch {
                    _events.send(DashboardEvent.NavigateToAppSettings)
                }
            }
            is DashboardAction.OnRefresh -> {
                fetchStatus()
            }
            is DashboardAction.DismissAlarmDialog -> {
                _state.update { it.copy(showAlarmDialog = false) }
            }
            is DashboardAction.SnoozeAlarm -> {
                val padId = _state.value.alarmSensorPadId
                if (padId.isNotBlank()) {
                    val snoozeMs = pollingIntervalSecs * 1000 * 60 // simplified snooze
                    zensiPreferences.setSnooze(padId, currentTimeMillis() + snoozeMs)
                }
                _state.update { it.copy(showAlarmDialog = false) }
            }
            is DashboardAction.DismissDisconnectedDialog -> {
                _state.update { it.copy(showDisconnectedDialog = false) }
            }
            is DashboardAction.DismissPinDialog -> {
                _state.update { it.copy(showPinDialog = false, pendingSettingsPadId = null) }
            }
            is DashboardAction.OnPinEntered -> {
                if (action.pin == storedPin) {
                    val padId = _state.value.pendingSettingsPadId
                    _state.update { it.copy(showPinDialog = false, pendingSettingsPadId = null) }
                    if (padId != null) {
                        viewModelScope.launch {
                            _events.send(DashboardEvent.NavigateToSensorSettings(padId))
                        }
                    }
                }
            }
        }
    }

    private fun startPolling() {
        pollingJob?.cancel()
        pollingJob = viewModelScope.launch {
            while (true) {
                fetchStatus()
                delay(pollingIntervalSecs * 1000)
            }
        }
    }

    private fun fetchStatus() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = _state.value.sensorList.isEmpty()) }

            when (val result = sensorRepository.getStatus(zensiPreferences.deviceId)) {
                is Result.Success -> {
                    val status = result.data
                    val sensors = status.flatSensorList()

                    // Update polling interval from app parameters
                    status.appParameters?.let { params ->
                        pollingIntervalSecs = params.pollingSecs
                    }

                    // Check for alarm triggers
                    val triggeredSensor = sensors.firstOrNull { it.alertTriggered }
                    val showAlarm = triggeredSensor != null &&
                            zensiPreferences.getSnoozeTime(triggeredSensor.padId) < currentTimeMillis()

                    _state.update {
                        it.copy(
                            sensorList = sensors,
                            userName = status.user,
                            plugins = status.plugins,
                            alertTimeColors = status.alertTimeColors,
                            buttonStatusColors = status.buttonStatusColors,
                            isLoading = false,
                            connectionLost = false,
                            lastUpdateTime = formatCurrentTime(),
                            showAlarmDialog = showAlarm,
                            alarmSensorName = triggeredSensor?.displayName ?: "",
                            alarmSensorPadId = triggeredSensor?.padId ?: ""
                        )
                    }
                }
                is Result.Error -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            connectionLost = true,
                            showDisconnectedDialog = it.sensorList.isNotEmpty()
                        )
                    }
                }
            }
        }
    }

    private fun fetchLogo() {
        viewModelScope.launch {
            when (val result = sensorRepository.getLogo()) {
                is Result.Success -> {
                    val logoUrl = result.data.logo
                    zensiPreferences.logoUrl = logoUrl
                    _state.update { it.copy(logoUrl = logoUrl) }
                }
                is Result.Error -> { /* keep cached logo */ }
            }
        }
    }

    private fun fetchTimeslots() {
        viewModelScope.launch {
            when (val result = sensorRepository.getTimeslots()) {
                is Result.Success -> {
                    zensiPreferences.timeslots = result.data.timeslots
                }
                is Result.Error -> { /* keep cached */ }
            }
        }
    }

    private fun fetchPin() {
        viewModelScope.launch {
            when (val result = sensorRepository.getUserPin()) {
                is Result.Success -> {
                    storedPin = result.data.pin
                    zensiPreferences.userPin = result.data.pin
                }
                is Result.Error -> {
                    storedPin = zensiPreferences.userPin
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        pollingJob?.cancel()
    }
}

// Platform-agnostic time utilities
expect fun currentTimeMillis(): Long
expect fun formatCurrentTime(): String
