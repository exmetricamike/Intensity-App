package com.intensityrecord.sensor.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LogoResponseDto(
    @SerialName("logo")
    val logo: String = ""
)
