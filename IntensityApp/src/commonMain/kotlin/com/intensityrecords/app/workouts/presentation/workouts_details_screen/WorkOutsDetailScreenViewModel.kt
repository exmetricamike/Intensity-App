package com.intensityrecords.app.workouts.presentation.workouts_details_screen

import androidx.lifecycle.ViewModel
import com.intensityrecords.app.workouts.domain.Session
import com.intensityrecords.app.workouts.domain.workoutCategories
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class WorkOutsDetailScreenViewModel : ViewModel() {
    private val _state = MutableStateFlow(WorkOutsDetailState())
    val state = _state.asStateFlow()

    fun initialize(workoutId: String) {
        val selectedWorkout = workoutCategories.find { it.title == workoutId }

        // Generate dummy sessions (matching your previous logic)
        val dummySessions = selectedWorkout?.let { item ->
            List(5) { index ->
                Session(
                    id = index,
                    title = "${item.title} #${index + 1}",
                    duration = "20 min",
                    level = "Medium",
                    image = item.image
                )
            }
        } ?: emptyList()

        _state.update {
            it.copy(
                item = selectedWorkout,
                sessions = dummySessions
            )
        }
    }

    fun onAction(action: WorkOutsDetailAction) {
        when (action) {
            WorkOutsDetailAction.OnCoachChooseClick -> { /* Handle coach logic */
            }

            is WorkOutsDetailAction.OnSessionClick -> { /* Handle session click */
            }

            WorkOutsDetailAction.OnBackClick -> { /* No-op in VM, handled in Root */
            }
        }
    }
}