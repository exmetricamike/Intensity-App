package com.intensityrecords.app.steptrip.presentation.step_trip_detail_screen

import com.intensityrecords.app.steptrip.domain.StepTripItem

sealed interface StepTripDetailAction {
    data object OnBackClick : StepTripDetailAction
    data object OnLetsGoClick : StepTripDetailAction

    data class OnSelectedWorkOutChange(val workoutItem: StepTripItem) : StepTripDetailAction

}