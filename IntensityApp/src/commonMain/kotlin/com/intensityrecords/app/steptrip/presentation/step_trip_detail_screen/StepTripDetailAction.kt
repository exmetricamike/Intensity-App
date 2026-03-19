package com.intensityrecords.app.steptrip.presentation.step_trip_detail_screen

import com.intensityrecords.app.steptrip.domain.StepTripItem
import com.intensityrecords.app.workouts.domain.WorkoutItem
import com.intensityrecords.app.workouts.presentation.workouts_details_screen.WorkOutsDetailAction

sealed interface StepTripDetailAction {
    data object OnBackClick : StepTripDetailAction

    data class OnSelectedWorkOutChange(val workoutItem: StepTripItem) : StepTripDetailAction

}