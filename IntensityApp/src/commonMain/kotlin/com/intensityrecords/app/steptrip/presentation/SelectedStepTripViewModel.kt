package com.intensityrecords.app.steptrip.presentation

import androidx.lifecycle.ViewModel
import com.intensityrecords.app.steptrip.domain.StepTripItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SelectedStepTripViewModel : ViewModel() {

    private val _selectedStepTrip = MutableStateFlow<StepTripItem?>(null)
    val selectedStepTrip = _selectedStepTrip.asStateFlow()

    fun onSelectStepTrip(stepTrip: StepTripItem?) {
        _selectedStepTrip.value = stepTrip
    }

}