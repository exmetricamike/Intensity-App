package com.intensityrecords.app.workouts.presentation.workouts_screen

import com.intensityrecords.app.workouts.domain.WorkoutSection

sealed interface WorkOutsAction {
    data class OnWorkoutClick(val workout: WorkoutSection) : WorkOutsAction

}