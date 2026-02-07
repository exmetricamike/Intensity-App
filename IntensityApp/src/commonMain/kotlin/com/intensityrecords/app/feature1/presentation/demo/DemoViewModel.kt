package com.intensityrecord.sensor.presentation.demo

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DemoViewModel : ViewModel() {

    private val _state = MutableStateFlow(DemoState())
    val state = _state.asStateFlow()

    fun onAction(action: DemoAction) {
        when (action) {
            is DemoAction.Next -> {
                _state.update {
                    if (it.canGoNext) it.copy(currentStep = it.currentStep + 1) else it
                }
            }
            is DemoAction.Previous -> {
                _state.update {
                    if (it.canGoPrevious) it.copy(currentStep = it.currentStep - 1) else it
                }
            }
            is DemoAction.RequestDemo -> {
                // In the future, this could open a URL or send a request
            }
            is DemoAction.OnBackClick -> { /* handled by navigation */ }
        }
    }
}
