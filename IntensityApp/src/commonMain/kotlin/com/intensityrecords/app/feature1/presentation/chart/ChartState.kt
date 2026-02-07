package com.intensityrecord.sensor.presentation.chart

data class ChartState(
    val sensorName: String = "",
    val htmlContent: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
