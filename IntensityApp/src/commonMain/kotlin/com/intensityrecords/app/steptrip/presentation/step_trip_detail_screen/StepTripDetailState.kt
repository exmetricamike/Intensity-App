package com.intensityrecords.app.steptrip.presentation.step_trip_detail_screen

import com.intensityrecords.app.steptrip.domain.StepTripItem

data class StepTripDetailState(
    val item: StepTripItem? = null,
    val isLoading: Boolean = false
)