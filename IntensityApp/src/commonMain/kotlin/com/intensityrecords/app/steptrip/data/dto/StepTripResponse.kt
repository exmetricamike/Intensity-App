package com.intensityrecords.app.steptrip.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class StepTripResponse(
    val stepTrip: List<StepTripDto>
)