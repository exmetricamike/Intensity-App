package com.intensityrecord.sensor.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PinResponseDto(
    @SerialName("result")
    val result: String = "",
    @SerialName("pin")
    val pin: String = ""
)
