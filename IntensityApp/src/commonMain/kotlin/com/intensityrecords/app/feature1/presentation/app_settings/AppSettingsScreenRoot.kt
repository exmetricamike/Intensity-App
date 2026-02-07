package com.intensityrecord.sensor.presentation.app_settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun AppSettingsScreenRoot(
    viewModel: AppSettingsViewModel,
    onBackClick: () -> Unit,
    onLogout: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is AppSettingsEvent.LoggedOut -> onLogout()
            }
        }
    }

    AppSettingsScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is AppSettingsAction.OnBackClick -> onBackClick()
                else -> viewModel.onAction(action)
            }
        }
    )
}
