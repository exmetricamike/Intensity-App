package com.intensityrecord.sensor.presentation.calibrate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.intensityrecord.core.domain.Result
import com.intensityrecord.sensor.domain.SensorRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed interface CalibrateEvent {
    data object Finished : CalibrateEvent
    data object Cancelled : CalibrateEvent
}

class CalibrateViewModel(
    private val padId: String,
    private val sensorRepository: SensorRepository
) : ViewModel() {

    private val _state = MutableStateFlow(CalibrateState(sensorPadId = padId))
    val state = _state.asStateFlow()

    private val _events = Channel<CalibrateEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: CalibrateAction) {
        when (action) {
            is CalibrateAction.Next -> {
                _state.update {
                    if (it.canGoNext) it.copy(currentStep = it.currentStep + 1) else it
                }
            }
            is CalibrateAction.Previous -> {
                _state.update {
                    if (it.canGoPrevious) it.copy(currentStep = it.currentStep - 1) else it
                }
            }
            is CalibrateAction.Cancel -> {
                viewModelScope.launch { _events.send(CalibrateEvent.Cancelled) }
            }
            is CalibrateAction.DoCalibrate -> calibrate()
            is CalibrateAction.Finish -> {
                viewModelScope.launch { _events.send(CalibrateEvent.Finished) }
            }
        }
    }

    private fun calibrate() {
        viewModelScope.launch {
            _state.update { it.copy(isCalibrating = true, isError = false) }

            when (sensorRepository.calibrateSensor(padId)) {
                is Result.Success -> {
                    _state.update { it.copy(isCalibrating = false, isSuccess = true) }
                }
                is Result.Error -> {
                    _state.update {
                        it.copy(
                            isCalibrating = false,
                            isError = true,
                            errorMessage = "Calibration failed. Please try again."
                        )
                    }
                }
            }
        }
    }
}
