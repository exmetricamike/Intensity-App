package com.intensityrecord.di

import com.intensityrecords.app.home.presentation.home_screen.HomeScreenViewModel
import com.intensityrecords.app.live.presentation.live_screen.LiveScreenViewModel
import com.intensityrecords.app.mobility.presentation.mobility_screen.MobilityScreenViewModel
import com.intensityrecords.app.workouts.presentation.workouts_details_screen.WorkOutsDetailScreenViewModel
import com.intensityrecords.app.workouts.presentation.workouts_screen.WorkOutsScreenViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule = module {

    viewModelOf(::HomeScreenViewModel)
    viewModelOf(::LiveScreenViewModel)
    viewModelOf(::MobilityScreenViewModel)
    viewModelOf(::WorkOutsScreenViewModel)
    viewModelOf(::WorkOutsDetailScreenViewModel)
}
