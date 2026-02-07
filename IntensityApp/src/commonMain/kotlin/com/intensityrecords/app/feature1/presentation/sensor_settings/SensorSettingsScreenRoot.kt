package com.intensityrecord.sensor.presentation.sensor_settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun SensorSettingsScreenRoot(
    viewModel: SensorSettingsViewModel,
    onBackClick: () -> Unit,
    onCalibrateClick: (String) -> Unit,
    onChartClick: (String) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is SensorSettingsEvent.SaveSuccess -> onBackClick()
                is SensorSettingsEvent.NavigateToCalibrate -> onCalibrateClick(event.padId)
                is SensorSettingsEvent.NavigateToChart -> onChartClick(event.sensorName)
            }
        }
    }

    SensorSettingsScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is SensorSettingsAction.OnBackClick -> onBackClick()
                else -> viewModel.onAction(action)
            }
        }
    )
}
