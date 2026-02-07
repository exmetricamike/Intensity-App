package com.intensityrecord.sensor.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TelemetryDto(
    @SerialName("battery_level")
    val batteryLevel: String = "",
    @SerialName("wifi_strength")
    val wifiStrength: String = "",
    @SerialName("app_version")
    val appVersion: String = ""
)
