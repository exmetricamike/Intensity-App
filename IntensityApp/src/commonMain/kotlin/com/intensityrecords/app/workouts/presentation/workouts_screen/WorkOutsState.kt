package com.intensityrecords.app.workouts.presentation.workouts_screen

import com.intensityrecords.app.workouts.domain.WorkoutSection

data class WorkoutsState(
    val isLoading: Boolean = false,
    val sections: List<WorkoutSection> = emptyList(),
    val error: String? = null
)
