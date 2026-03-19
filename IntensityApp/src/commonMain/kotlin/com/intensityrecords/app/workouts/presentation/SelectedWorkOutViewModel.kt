package com.intensityrecords.app.workouts.presentation

import androidx.lifecycle.ViewModel
import com.intensityrecords.app.workouts.domain.WorkoutItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SelectedWorkOutViewModel : ViewModel() {

    private val _selectedWorkOut = MutableStateFlow<WorkoutItem?>(null)
    val selectedWorkOut = _selectedWorkOut.asStateFlow()

    fun onSelectWorkOut(workOut: WorkoutItem?) {
        _selectedWorkOut.value = workOut
    }

}