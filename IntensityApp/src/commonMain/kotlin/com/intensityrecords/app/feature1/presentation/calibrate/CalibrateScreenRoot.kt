package com.intensityrecord.sensor.presentation.calibrate

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun CalibrateScreenRoot(
    viewModel: CalibrateViewModel,
    onFinish: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is CalibrateEvent.Finished -> onFinish()
                is CalibrateEvent.Cancelled -> onFinish()
            }
        }
    }

    CalibrateScreen(
        state = state,
        onAction = viewModel::onAction
    )
}
