package com.intensityrecords.app.steptrip.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class StepTripDto(
    val id: Int,
    val title: String,
    val category: String,
    val duration: String,
    val distance: String,
    val calories: String,
    val image: String
)