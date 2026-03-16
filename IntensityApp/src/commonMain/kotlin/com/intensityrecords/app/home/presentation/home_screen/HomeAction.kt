package com.intensityrecords.app.home.presentation.home_screen

import com.intensityrecords.app.home.domain.UiConfig

sealed interface HomeAction {

    data object LoadData : HomeAction

    data class OnItemClick(
        val item: UiConfig
    ) : HomeAction

}