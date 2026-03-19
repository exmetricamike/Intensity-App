package com.intensityrecords.app.steptrip.presentation.steptrip

import com.intensityrecords.app.steptrip.domain.StepTripItem
import com.intensityrecords.app.steptrip.domain.TripData
import com.intensityrecords.app.workouts.presentation.workouts_screen.WorkOutsAction

sealed interface StepTripAction {
    data class OnStripTripClick(val tripData: StepTripItem) : StepTripAction

    object LoadWorkouts : StepTripAction

}