package com.intensityrecord.sensor.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserActionDto(
    @SerialName("sensor_id")
    val sensorId: String? = null,
    @SerialName("action")
    val action: String,
    @SerialName("parameter")
    val parameter: String = "",
    @SerialName("device_id")
    val deviceId: String = ""
)
