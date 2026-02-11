package com.intensityrecord.app

import kotlinx.serialization.Serializable

sealed interface Route {

    // --- Zensi sensor monitoring ---
    @Serializable
    data class SensorSettings(val padId: String) : Route

    @Serializable
    data class Calibrate(val padId: String) : Route

    @Serializable
    data class Chart(val sensorName: String) : Route

    @Serializable
    data class BookDetail(val id: String) : Route

    @Serializable
    data object Home : Route

    @Serializable
    data object Live : Route

    @Serializable
    data object WorkOuts : Route

    @Serializable
    data object Mobility : Route

    @Serializable
    data object VideoDetail : Route

    @Serializable
    data class WorkOutsDetailsScreen(val id: String) : Route
}