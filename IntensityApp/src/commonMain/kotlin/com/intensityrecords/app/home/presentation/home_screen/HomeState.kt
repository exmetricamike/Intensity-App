package com.intensityrecords.app.home.presentation.home_screen

import com.intensityrecords.app.home.domain.DailyVideo
import com.intensityrecords.app.home.domain.UiConfig

sealed interface DailyVideoUiState {
    data object Loading : DailyVideoUiState
    data class Available(val video: DailyVideo) : DailyVideoUiState
    data object NotAvailable : DailyVideoUiState
    data object Error : DailyVideoUiState
}

data class HomeState(
    val isLoading: Boolean = false,
    val items: UiConfig? = null,
    val errorMessage: String? = null,
    val dailyVideoState: DailyVideoUiState = DailyVideoUiState.Loading
)
