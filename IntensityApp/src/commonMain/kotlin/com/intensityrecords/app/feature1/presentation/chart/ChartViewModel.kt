package com.intensityrecord.sensor.presentation.chart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.intensityrecord.core.domain.Result
import com.intensityrecord.sensor.domain.SensorRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChartViewModel(
    private val sensorName: String,
    private val sensorRepository: SensorRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ChartState(sensorName = sensorName))
    val state = _state.asStateFlow()

    init {
        loadChart()
    }

    private fun loadChart() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            when (val result = sensorRepository.getChart(sensorName)) {
                is Result.Success -> {
                    _state.update {
                        it.copy(isLoading = false, htmlContent = result.data)
                    }
                }
                is Result.Error -> {
                    _state.update {
                        it.copy(isLoading = false, error = "Failed to load chart")
                    }
                }
            }
        }
    }
}
