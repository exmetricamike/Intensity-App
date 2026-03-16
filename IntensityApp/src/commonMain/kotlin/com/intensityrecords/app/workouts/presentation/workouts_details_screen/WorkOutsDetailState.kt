package com.intensityrecords.app.workouts.presentation.workouts_details_screen

import com.intensityrecords.app.workouts.domain.CollectionDetail
import com.intensityrecords.app.workouts.domain.WorkoutVideo

data class WorkoutDetailState(
    val isLoading: Boolean = false,
    val collection: CollectionDetail? = null,
    val error: String? = null
)
