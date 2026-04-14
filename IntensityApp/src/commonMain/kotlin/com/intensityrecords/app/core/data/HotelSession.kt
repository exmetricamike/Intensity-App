package com.intensityrecords.app.core.data

import com.intensityrecords.app.home.domain.HotelTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HotelSession {

    private val _theme = MutableStateFlow(defaultTheme)
    val theme: StateFlow<HotelTheme> = _theme.asStateFlow()

    fun setTheme(theme: HotelTheme) {
        _theme.value = theme
    }

    fun clearTheme() {
        _theme.value = defaultTheme
    }

    companion object {
        val defaultTheme = HotelTheme(
            id = 0,
            hotelName = "Hotel",
            hotelUrlName = "",
            hotelLogo = null,
            hotelTagline = "",
            showLogo = true,
            showHeader = true,
            primaryColor = null,
            secondaryColor = null,
            headerTextColor = null,
            titleTextColor = null,
            textColor = null
        )
    }
}
