package com.intensityrecord.sensor.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserActionResponseDto(
    @SerialName("result")
    val result: String = ""
)
