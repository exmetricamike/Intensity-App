package com.intensityrecord.sensor.presentation.app_settings

import androidx.lifecycle.ViewModel
import com.intensityrecord.core.data.ApiEndpointResolver
import com.intensityrecord.core.data.ZensiPreferences
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update

sealed interface AppSettingsEvent {
    data object LoggedOut : AppSettingsEvent
}

class AppSettingsViewModel(
    private val zensiPreferences: ZensiPreferences
) : ViewModel() {

    private val _state = MutableStateFlow(
        AppSettingsState(
            numColumns = zensiPreferences.numColumns,
            soundEnabled = zensiPreferences.soundEnabled,
            vibrateEnabled = zensiPreferences.vibrateEnabled
        )
    )
    val state = _state.asStateFlow()

    private val _events = Channel<AppSettingsEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: AppSettingsAction) {
        when (action) {
            is AppSettingsAction.ChangeColumns -> {
                zensiPreferences.numColumns = action.columns
                _state.update { it.copy(numColumns = action.columns) }
            }
            is AppSettingsAction.ToggleSound -> {
                val newValue = !_state.value.soundEnabled
                zensiPreferences.soundEnabled = newValue
                _state.update { it.copy(soundEnabled = newValue) }
            }
            is AppSettingsAction.ToggleVibrate -> {
                val newValue = !_state.value.vibrateEnabled
                zensiPreferences.vibrateEnabled = newValue
                _state.update { it.copy(vibrateEnabled = newValue) }
            }
            is AppSettingsAction.Logout -> {
                zensiPreferences.clear()
                ApiEndpointResolver.clear()
                _events.trySend(AppSettingsEvent.LoggedOut)
            }
            is AppSettingsAction.OnBackClick -> { /* handled by navigation */ }
        }
    }
}
