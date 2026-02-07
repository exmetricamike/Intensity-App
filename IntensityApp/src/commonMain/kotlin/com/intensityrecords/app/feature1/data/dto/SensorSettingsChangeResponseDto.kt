package com.intensityrecord.sensor.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SensorSettingsChangeResponseDto(
    @SerialName("result")
    val result: String = "",
    @SerialName("sensor_id")
    val sensorId: String = "",
    @SerialName("user")
    val user: String = ""
)
