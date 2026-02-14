package com.intensityrecords.app.workouts.presentation.workouts_screen

import androidx.lifecycle.ViewModel
import com.intensityrecords.app.workouts.domain.workoutCategories
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class WorkOutsScreenViewModel() : ViewModel() {

    private val _state = MutableStateFlow(WorkOutsState())
    val state = _state.asStateFlow()

    init {
        _state.update { it.copy(workouts = workoutCategories) }
    }

    fun onAction(action: WorkOutsAction) {
        when (action) {
            is WorkOutsAction.OnWorkoutClick -> {
                // Handle logic if needed (e.g., logging) before navigation
            }
        }
    }

}