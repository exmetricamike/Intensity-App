package com.intensityrecords.app.app

import kotlinx.serialization.Serializable

sealed interface Route {

    @Serializable
    data object Login : Route

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
    data class WorkOutsDetailsScreen(val id: Int) : Route

    @Serializable
    data object TimeTable : Route

    @Serializable
    data object StepTrip : Route

    @Serializable
    data class StepTripDetailScreen(val id: Int) : Route

    @Serializable
    data object Programs : Route

    @Serializable
    data class ProgramDetailsScreen(val id: Int) : Route
}