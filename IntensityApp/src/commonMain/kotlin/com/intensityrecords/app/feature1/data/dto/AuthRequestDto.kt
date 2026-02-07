package com.intensityrecord.sensor.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthRequestDto(
    @SerialName("username")
    val username: String,
    @SerialName("password")
    val password: String
)
