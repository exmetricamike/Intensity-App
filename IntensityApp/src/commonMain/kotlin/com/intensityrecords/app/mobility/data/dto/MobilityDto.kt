package com.intensityrecords.app.mobility.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class MobilityDto(
    val id: Int,
    val title: String,
    val duration: String,
    val image: String
)