package com.intensityrecord.sensor.presentation.chart

sealed interface ChartAction {
    data object OnBackClick : ChartAction
}
