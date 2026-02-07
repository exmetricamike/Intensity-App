package com.intensityrecord.sensor.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FirebaseDeviceTokenDto(
    @SerialName("firebase_device_token")
    val firebaseDeviceToken: String = "",
    @SerialName("jwt_auth_token")
    val jwtAuthToken: String = "",
    @SerialName("feature1_unique_device_id")
    val zensiUniqueDeviceId: String = ""
)
