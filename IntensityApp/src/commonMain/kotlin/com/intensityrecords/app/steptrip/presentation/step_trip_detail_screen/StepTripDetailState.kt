package com.intensityrecords.app.steptrip.presentation.step_trip_detail_screen

import com.intensityrecords.app.steptrip.domain.TripData
import com.intensityrecords.app.workouts.domain.Session
import com.intensityrecords.app.workouts.domain.WorkoutItem

data class StepTripDetailState(
    val item: TripData? = null,
    val isLoading: Boolean = false
)