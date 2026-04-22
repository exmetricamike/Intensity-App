package com.intensityrecords.app.steptrip.presentation.steptrip

import com.intensityrecords.app.steptrip.domain.StepTripItem

sealed interface StepTripAction {
    data class OnStripTripClick(val tripData: StepTripItem) : StepTripAction
    object LoadWorkouts : StepTripAction
}
