package com.intensityrecords.app.steptrip.presentation.step_trip_detail_screen

sealed interface StepTripDetailAction {
    data object OnBackClick : StepTripDetailAction
}