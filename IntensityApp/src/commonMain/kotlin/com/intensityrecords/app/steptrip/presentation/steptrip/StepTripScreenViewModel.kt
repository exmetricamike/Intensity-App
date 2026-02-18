package com.intensityrecords.app.steptrip.presentation.steptrip

import androidx.lifecycle.ViewModel
import com.intensityrecords.app.steptrip.domain.trips
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class StepTripScreenViewModel(

): ViewModel(){

    private val _state = MutableStateFlow(StripTripState())
    val state = _state.asStateFlow()

    init {
        _state.update { it.copy(tripData = trips) }
    }

    fun onAction(action: StepTripAction) {
        when (action) {
            is StepTripAction.OnStripTripClick -> {
                // Handle logic if needed (e.g., logging) before navigation
            }
        }
    }

}