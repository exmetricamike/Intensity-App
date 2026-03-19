package com.intensityrecords.app.mobility.presentation.mobility_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.intensityrecords.app.core.domain.onError
import com.intensityrecords.app.core.domain.onSuccess
import com.intensityrecords.app.mobility.domain.MobilityRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MobilityScreenViewModel(
    private val repository: MobilityRepository
) : ViewModel() {

    private val _state = MutableStateFlow(MobilityState())
    val state = _state.asStateFlow()

    init {
        loadMobilityData()
    }

    private fun loadMobilityData() {

        _state.update { it.copy(loading = true) }

        viewModelScope.launch {

            val result = repository.getMobility()

            result.onSuccess { mobilityData ->
                _state.update {
                    it.copy(
                        loading = false,
                        mobilityData = mobilityData
                    )
                }
            }

            result.onError { error ->
                _state.update {
                    it.copy(
                        loading = false,
                        error = "Failed to load mobility data \n $error"
                    )
                }
            }

        }

    }

    fun onAction(action: MobilityAction) {
        when (action) {
            MobilityAction.LoadMobility -> {
                if (state.value.mobilityData.isNotEmpty()) return
                loadMobilityData()
            }

            is MobilityAction.OnClick -> {

            }
        }
    }

}