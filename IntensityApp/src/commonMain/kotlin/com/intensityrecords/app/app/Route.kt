package com.intensityrecord.app

import kotlinx.serialization.Serializable

sealed interface Route {

    // --- Zensi sensor monitoring ---
    @Serializable
    data object ZensiGraph : Route

    @Serializable
    data object Login : Route

    @Serializable
    data object Dashboard : Route

    @Serializable
    data class SensorSettings(val padId: String) : Route

    @Serializable
    data class Calibrate(val padId: String) : Route

    @Serializable
    data class Chart(val sensorName: String) : Route

    @Serializable
    data object AppSettings : Route

    @Serializable
    data object Demo : Route

    // --- Book feature (reference) ---
    @Serializable
    data object BookGraph : Route

    @Serializable
    data object BookList : Route

    @Serializable
    data class BookDetail(val id: String) : Route
}
