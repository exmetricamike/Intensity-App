package com.intensityrecords.app.app

import kotlinx.serialization.Serializable

sealed interface Route {

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

    @Serializable
    data object TimeTable : Route

    @Serializable
    data object StepTrip : Route

    @Serializable
    data class StepTripDetailScreen(val id: String) : Route
}