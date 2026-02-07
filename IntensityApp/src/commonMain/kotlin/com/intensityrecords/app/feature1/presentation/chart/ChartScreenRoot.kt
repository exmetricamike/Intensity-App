package com.intensityrecord.sensor.presentation.chart

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun ChartScreenRoot(
    viewModel: ChartViewModel,
    onBackClick: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ChartScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is ChartAction.OnBackClick -> onBackClick()
            }
        }
    )
}
