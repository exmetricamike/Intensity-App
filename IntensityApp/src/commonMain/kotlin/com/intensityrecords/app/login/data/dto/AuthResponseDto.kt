package com.intensityrecords.app.login.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthResponseDto(
    val token: String,
    @SerialName("hotel_id") val hotelId: String
)