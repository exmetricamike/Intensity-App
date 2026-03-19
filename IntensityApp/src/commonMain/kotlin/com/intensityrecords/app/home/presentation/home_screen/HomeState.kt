package com.intensityrecords.app.home.presentation.home_screen

import com.intensityrecords.app.home.domain.UiConfig

data class HomeState(
    val isLoading: Boolean = false,
    val items: UiConfig? = null,
    val errorMessage: String? = null
)