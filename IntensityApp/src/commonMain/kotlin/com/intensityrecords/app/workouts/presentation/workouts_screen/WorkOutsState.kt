package com.intensityrecords.app.workouts.presentation.workouts_screen

import com.intensityrecords.app.workouts.domain.WorkoutItem

data class WorkOutsState(
    val workouts: List<WorkoutItem> = emptyList(),
    val isLoading: Boolean = false
)