package com.intensityrecords.app.program.presentation.program_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.intensityrecords.app.core.data.SessionProvider
import com.intensityrecords.app.core.domain.onError
import com.intensityrecords.app.core.domain.onSuccess
import com.intensityrecords.app.program.domain.ProgramRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProgramsScreenViewModel(
    private val repository: ProgramRepository,
    private val sessionProvider: SessionProvider
) : ViewModel() {

    private val _state = MutableStateFlow(ProgramsState())
    val state: StateFlow<ProgramsState> = _state

    init {
        loadPrograms()
    }

    private fun loadPrograms() {
        _state.value = _state.value.copy(isLoading = true)

        viewModelScope.launch {
            repository.getPrograms(sessionProvider.getAuthId() ?: "")
                .onSuccess { data ->
                    _state.value = ProgramsState(
                        isLoading = false,
                        sections = data
                    )
                }
                .onError {
                    _state.value = ProgramsState(
                        isLoading = false,
                        error = "Failed to load programs"
                    )
                }
        }
    }
}
