package com.intensityrecords.app.home.presentation.home_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.intensityrecords.app.core.data.HotelSession
import com.intensityrecords.app.core.data.SessionProvider
import com.intensityrecords.app.core.domain.DataError
import com.intensityrecords.app.core.domain.onError
import com.intensityrecords.app.core.domain.onSuccess
import com.intensityrecords.app.home.domain.HomeRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val homeRepository: HomeRepository,
    private val sessionProvider: SessionProvider,
    private val hotelSession: HotelSession
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState(isLoading = true))
    val state: StateFlow<HomeState> = _state.asStateFlow()

    init {
        observeAuth()
    }

    private fun observeAuth() {
        viewModelScope.launch {
            sessionProvider.authId.collect { id ->
                if (id != null) {
                    loadHomeData(id)
                }
            }
        }
    }

    private fun loadHomeData(hotelId: String) {
        _state.update { it.copy(isLoading = true, dailyVideoState = DailyVideoUiState.Loading) }

        viewModelScope.launch {
            val homeDeferred = async { homeRepository.getHome(hotelId) }
            val dailyVideoDeferred = async { homeRepository.getDailyVideo(hotelId) }

            val homeResult = homeDeferred.await()
            val dailyVideoResult = dailyVideoDeferred.await()

            homeResult.onSuccess { data ->
                data.theme?.let { hotelSession.setTheme(it) }
                _state.update { it.copy(isLoading = false, items = data) }
            }
            homeResult.onError {
                _state.update { it.copy(isLoading = false, errorMessage = "Failed to load data") }
            }

            dailyVideoResult.onSuccess { video ->
                _state.update { it.copy(dailyVideoState = DailyVideoUiState.Available(video)) }
            }
            dailyVideoResult.onError { error ->
                val newVideoState = if (error == DataError.Remote.NOT_FOUND) {
                    DailyVideoUiState.NotAvailable
                } else {
                    DailyVideoUiState.Error
                }
                _state.update { it.copy(dailyVideoState = newVideoState) }
            }
        }
    }
}
