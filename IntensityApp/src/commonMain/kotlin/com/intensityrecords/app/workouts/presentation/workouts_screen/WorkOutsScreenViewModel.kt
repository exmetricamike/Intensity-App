package com.intensityrecords.app.workouts.presentation.workouts_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.intensityrecords.app.core.data.SessionProvider
import com.intensityrecords.app.core.domain.onError
import com.intensityrecords.app.core.domain.onSuccess
import com.intensityrecords.app.workouts.domain.WorkoutRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WorkOutsScreenViewModel(
    private val repository: WorkoutRepository,
    private val sessionProvider: SessionProvider
) : ViewModel() {

    private val _state = MutableStateFlow(WorkoutsState())
    val state: StateFlow<WorkoutsState> = _state

    init {
        loadWorkouts()
    }

    private fun loadWorkouts() {

        _state.value = _state.value.copy(isLoading = true)

        viewModelScope.launch {

            repository.getWorkouts(sessionProvider.getAuthId() ?: "")
                .onSuccess { data ->

                    _state.value = WorkoutsState(
                        isLoading = false,
                        sections = data
                    )
                }
                .onError {

                    _state.value = WorkoutsState(
                        isLoading = false,
                        error = "Failed to load workouts"
                    )
                }
        }
    }
}
