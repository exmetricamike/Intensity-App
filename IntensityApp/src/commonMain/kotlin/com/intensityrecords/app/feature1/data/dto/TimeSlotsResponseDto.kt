package com.intensityrecord.sensor.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TimeSlotsResponseDto(
    @SerialName("status")
    val status: String = "",
    @SerialName("timeslots")
    val timeslots: List<List<String>> = emptyList()
)
