package com.intensityrecord.sensor.presentation.demo

sealed interface DemoAction {
    data object Next : DemoAction
    data object Previous : DemoAction
    data object RequestDemo : DemoAction
    data object OnBackClick : DemoAction
}
