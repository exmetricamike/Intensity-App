package com.intensityrecords.app.steptrip.presentation.steptrip

import com.intensityrecords.app.steptrip.domain.TripData

sealed interface StepTripAction {
    data class OnStripTripClick(val tripData: TripData) : StepTripAction
}