package com.intensityrecord.sensor.data.dto

import com.intensityrecord.sensor.domain.AppParameters
import com.intensityrecord.sensor.domain.SensorData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StatusResponseDto(
    @SerialName("result")
    val result: String = "",
    @SerialName("plugins")
    val plugins: List<String> = emptyList(),
    @SerialName("user")
    val user: String = "",
    @SerialName("num_sensors")
    val numSensors: Int = 0,
    @SerialName("alert-time-colors")
    val alertTimeColors: List<String> = emptyList(),
    @SerialName("button-status-colors")
    val buttonStatusColors: Map<String, String> = emptyMap(),
    @SerialName("data")
    val data: Map<String, Map<String, SensorData>> = emptyMap(),
    @SerialName("app-parametes")
    val appParameters: AppParameters? = null
) {
    fun flatSensorList(): List<SensorData> {
        return data.values.flatMap { it.values }.sortedBy { it.displayName }
    }
}
