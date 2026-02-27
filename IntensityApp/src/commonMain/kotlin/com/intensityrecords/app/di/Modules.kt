package com.intensityrecords.app.di

import com.intensityrecords.app.core.presentation.LanguageViewModel
import com.intensityrecords.app.home.presentation.home_screen.HomeScreenViewModel
import com.intensityrecords.app.live.presentation.live_screen.LiveScreenViewModel
import com.intensityrecords.app.mobility.presentation.mobility_screen.MobilityScreenViewModel
import com.intensityrecords.app.steptrip.presentation.steptrip.StepTripScreenViewModel
import com.intensityrecords.app.steptrip.presentation.step_trip_detail_screen.StepTripsDetailScreenViewModel
import com.intensityrecords.app.workouts.presentation.workouts_details_screen.WorkOutsDetailScreenViewModel
import com.intensityrecords.app.workouts.presentation.workouts_screen.WorkOutsScreenViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module


val sharedModule = module {

    viewModelOf(::HomeScreenViewModel)
    viewModelOf(::LiveScreenViewModel)
    viewModelOf(::MobilityScreenViewModel)
    viewModelOf(::WorkOutsScreenViewModel)
    viewModelOf(::WorkOutsDetailScreenViewModel)
    viewModelOf(::StepTripScreenViewModel)
    viewModelOf(::StepTripsDetailScreenViewModel)
    viewModelOf(::LanguageViewModel)
}
