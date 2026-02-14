package com.intensityrecords.app.workouts.presentation.workouts_details_screen

import com.intensityrecords.app.workouts.domain.Session
import com.intensityrecords.app.workouts.domain.WorkoutItem

data class WorkOutsDetailState(
    val item: WorkoutItem? = null,
    val sessions: List<Session> = emptyList(),
    val isLoading: Boolean = false
)