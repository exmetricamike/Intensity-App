package com.intensityrecords.app.workouts.presentation.workouts_details_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.intensityrecords.app.core.data.SessionProvider
import com.intensityrecords.app.core.domain.onError
import com.intensityrecords.app.core.domain.onSuccess
import com.intensityrecords.app.workouts.domain.WorkoutRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

//class WorkOutsDetailScreenViewModel(
//    private val repository: WorkoutRepository,
//    private val sessionProvider: SessionProvider
//) : ViewModel() {
//
//    private val _state = MutableStateFlow(WorkoutDetailState())
//    val state: StateFlow<WorkoutDetailState> = _state
//
//    fun loadCollection(collectionId: Int) {
//
//        _state.value = _state.value.copy(isLoading = true)
//
//        viewModelScope.launch {
//
//            repository.getWorkoutsCollection(
//                sessionProvider.getAuthId() ?: "",
//                collectionId
//            ).onSuccess { data ->
//
//                _state.value = WorkoutDetailState(
//                    isLoading = false,
//                    collection = data
//                )
//            }.onError {
//
//                _state.value = WorkoutDetailState(
//                    isLoading = false,
//                    error = "Failed to load videos"
//                )
//            }
//        }
//    }
//}

class WorkOutsDetailScreenViewModel(
    private val repository: WorkoutRepository,
    private val sessionProvider: SessionProvider
) : ViewModel() {

    private val _state = MutableStateFlow(WorkoutDetailState())
    val state: StateFlow<WorkoutDetailState> = _state

    fun loadCollection(collectionId: Int) {

        viewModelScope.launch {

            println("COLLECTION ID :: $collectionId")

            _state.value = _state.value.copy(isLoading = true)

            repository.getWorkoutsCollection(
                hotelId = sessionProvider.getAuthId() ?: "",
                collectionId
            ).onSuccess { videos ->
                println("COLLECTION VIDEOS :: $videos")
                _state.value = _state.value.copy(
                    isLoading = false,
                    collection = videos
                )
            }.onError {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = "Failed to load videos"
                )
            }
        }
    }
}

