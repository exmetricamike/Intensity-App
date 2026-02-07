package com.intensityrecord.sensor.presentation.dashboard

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun DashboardScreenRoot(
    viewModel: DashboardViewModel,
    onNavigateToSensorSettings: (String) -> Unit,
    onNavigateToAppSettings: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is DashboardEvent.NavigateToSensorSettings -> {
                    onNavigateToSensorSettings(event.padId)
                }
                is DashboardEvent.NavigateToAppSettings -> {
                    onNavigateToAppSettings()
                }
            }
        }
    }

    DashboardScreen(
        state = state,
        onAction = viewModel::onAction
    )
}
