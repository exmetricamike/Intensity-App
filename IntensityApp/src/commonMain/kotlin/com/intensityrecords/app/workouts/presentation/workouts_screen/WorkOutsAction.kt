package com.intensityrecords.app.workouts.presentation.workouts_screen

import com.intensityrecords.app.workouts.domain.WorkoutItem

sealed interface WorkOutsAction {
    data class OnWorkoutClick(val workout: WorkoutItem) : WorkOutsAction
}