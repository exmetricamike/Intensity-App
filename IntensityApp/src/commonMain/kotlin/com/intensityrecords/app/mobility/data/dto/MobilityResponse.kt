package com.intensityrecords.app.mobility.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class MobilityResponse(
    val mobility: List<MobilityDto>
)