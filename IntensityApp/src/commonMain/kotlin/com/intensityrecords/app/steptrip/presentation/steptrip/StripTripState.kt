package com.intensityrecords.app.steptrip.presentation.steptrip

import com.intensityrecords.app.steptrip.domain.TripData

data class StripTripState(
    val tripData: List<TripData> = emptyList(),
    val isLoading: Boolean = false
)