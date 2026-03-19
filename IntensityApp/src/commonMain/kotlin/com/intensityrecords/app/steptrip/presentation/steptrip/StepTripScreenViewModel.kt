package com.intensityrecords.app.steptrip.presentation.steptrip

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.intensityrecords.app.core.domain.onError
import com.intensityrecords.app.core.domain.onSuccess
import com.intensityrecords.app.steptrip.domain.StepTripRepository
import com.intensityrecords.app.steptrip.domain.trips
import com.intensityrecords.app.workouts.presentation.workouts_screen.WorkOutsAction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class StepTripScreenViewModel(
    private val repository: StepTripRepository
) : ViewModel() {

    private val _state = MutableStateFlow(StripTripState())
    val state = _state.asStateFlow()

//    init {
//        _state.update { it.copy(tripData = trips) }
//    }
//
//    fun onAction(action: StepTripAction) {
//        when (action) {
//            is StepTripAction.OnStripTripClick -> {
//                // Handle logic if needed (e.g., logging) before navigation
//            }
//        }
//    }

    init {
        loadWorkouts()
    }

    fun onAction(action: StepTripAction) {

        when (action) {

            StepTripAction.LoadWorkouts -> {
                if (state.value.tripData.isNotEmpty()) return
                loadWorkouts()
            }

            else -> {}
        }
    }

    private fun loadWorkouts() {

        _state.update { it.copy(loading = true) }

        viewModelScope.launch {

            val result = repository.getStepTrip()

            result.onSuccess { workouts ->

                println("Workouts received = $workouts")

                _state.update {
                    it.copy(
                        loading = false,
                        tripData = workouts
                    )
                }
            }

            result.onError {

                _state.update {
                    it.copy(
                        loading = false,
                        error = "Failed to load workouts"
                    )
                }
            }
        }

    }

}