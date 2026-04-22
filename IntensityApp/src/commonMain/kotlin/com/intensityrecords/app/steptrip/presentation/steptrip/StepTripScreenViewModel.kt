package com.intensityrecords.app.steptrip.presentation.steptrip

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.intensityrecords.app.core.data.SessionProvider
import com.intensityrecords.app.core.domain.onError
import com.intensityrecords.app.core.domain.onSuccess
import com.intensityrecords.app.steptrip.domain.StepTripRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class StepTripScreenViewModel(
    private val repository: StepTripRepository,
    private val sessionProvider: SessionProvider
) : ViewModel() {

    private val _state = MutableStateFlow(StripTripState())
    val state = _state.asStateFlow()

    init {
        observeAuth()
    }

    private fun observeAuth() {
        viewModelScope.launch {
            sessionProvider.authId.collect { hotelId ->
                if (hotelId != null) {
                    loadStepTrips(hotelId)
                }
            }
        }
    }

    private fun loadStepTrips(hotelId: String) {
        _state.update { it.copy(loading = true, error = null) }

        viewModelScope.launch {
            val result = repository.getStepTrips(hotelId)

            result.onSuccess { items ->
                _state.update { it.copy(loading = false, tripData = items) }
            }

            result.onError {
                _state.update { it.copy(loading = false, error = "Failed to load step trips") }
            }
        }
    }

    fun onAction(action: StepTripAction) {
        when (action) {
            StepTripAction.LoadWorkouts -> {
                if (state.value.tripData.isNotEmpty()) return
                sessionProvider.getAuthId()?.let { loadStepTrips(it) }
            }
            else -> {}
        }
    }
}
