package com.intensityrecords.app.steptrip.presentation.steptrip

import com.intensityrecords.app.steptrip.domain.StepTripItem

data class StripTripState(
    val tripData: List<StepTripItem> = emptyList(),
    val loading: Boolean = false,
    val error: String? = null
)
