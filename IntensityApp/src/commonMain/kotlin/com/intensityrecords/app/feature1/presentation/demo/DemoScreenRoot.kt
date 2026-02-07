package com.intensityrecord.sensor.presentation.demo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun DemoScreenRoot(
    viewModel: DemoViewModel,
    onBackClick: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    DemoScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is DemoAction.OnBackClick -> onBackClick()
                else -> viewModel.onAction(action)
            }
        }
    )
}
