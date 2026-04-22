package com.intensityrecords.app.steptrip.presentation.step_trip_detail_screen

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class StepTripsDetailScreenViewModel : ViewModel() {
    private val _state = MutableStateFlow(StepTripDetailState())
    val state = _state.asStateFlow()

//    fun initialize(workoutId: String) {
//        val selectedStepTrips = trips.find { it.title == workoutId }
//
//        _state.update {
//            it.copy(
//                item = selectedStepTrips
//            )
//        }
//    }

//    fun onAction(action: StepTripDetailAction) {
//        when (action) {
//            StepTripDetailAction.OnBackClick -> {
//
//            }
//        }
//    }

    fun onAction(action: StepTripDetailAction) {
        when (action) {
            StepTripDetailAction.OnBackClick -> {}
            is StepTripDetailAction.OnSelectedWorkOutChange -> {
                println("Detaisl :- ${action.workoutItem}")
                _state.update {
                    it.copy(
                        item = action.workoutItem
                    )
                }
            }
        }
    }

}