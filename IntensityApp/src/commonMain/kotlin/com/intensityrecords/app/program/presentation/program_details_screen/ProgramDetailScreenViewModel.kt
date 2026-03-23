package com.intensityrecords.app.program.presentation.program_details_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.intensityrecords.app.core.data.SessionProvider
import com.intensityrecords.app.core.domain.onError
import com.intensityrecords.app.core.domain.onSuccess
import com.intensityrecords.app.program.domain.ProgramRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProgramDetailScreenViewModel(
    private val repository: ProgramRepository,
    private val sessionProvider: SessionProvider
) : ViewModel() {

    private val _state = MutableStateFlow(ProgramDetailState())
    val state: StateFlow<ProgramDetailState> = _state

    fun loadCollection(collectionId: Int) {
        viewModelScope.launch {
            println("PROGRAM COLLECTION ID :: $collectionId")

            _state.value = _state.value.copy(isLoading = true)

            repository.getProgramCollection(
                hotelId = sessionProvider.getAuthId() ?: "",
                collectionId = collectionId
            ).onSuccess { data ->
                println("PROGRAM COLLECTION DATA :: $data")
                _state.value = _state.value.copy(
                    isLoading = false,
                    collection = data
                )
            }.onError {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = "Failed to load program videos"
                )
            }
        }
    }
}
