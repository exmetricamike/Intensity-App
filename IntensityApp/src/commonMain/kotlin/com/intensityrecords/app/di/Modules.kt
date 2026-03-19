package com.intensityrecords.app.di

import com.intensityrecords.app.core.data.HttpClientFactory
import com.intensityrecords.app.core.data.SessionProvider
import com.intensityrecords.app.core.presentation.LanguageViewModel
import com.intensityrecords.app.home.data.network.KtorRemoteHomeDataSource
import com.intensityrecords.app.home.data.network.RemoteHomeDataSource
import com.intensityrecords.app.home.data.repository.DefaultHomeRepository
import com.intensityrecords.app.home.domain.HomeRepository
import com.intensityrecords.app.home.presentation.home_screen.HomeScreenViewModel
import com.intensityrecords.app.live.presentation.live_screen.LiveScreenViewModel
import com.intensityrecords.app.login.data.network.KtorRemoteAuthDataSource
import com.intensityrecords.app.login.data.network.RemoteAuthDataSource
import com.intensityrecords.app.login.data.repository.DefaultAuthRepository
import com.intensityrecords.app.login.domain.AuthRepository
import com.intensityrecords.app.mobility.data.network.KtorRemoteMobilityDataSource
import com.intensityrecords.app.mobility.data.network.RemoteMobilityDataSource
import com.intensityrecords.app.mobility.data.repository.DefaultMobilityRepository
import com.intensityrecords.app.mobility.domain.MobilityRepository
import com.intensityrecords.app.mobility.presentation.mobility_screen.MobilityScreenViewModel
import com.intensityrecords.app.steptrip.data.network.KtorRemoteStepTripDataSource
import com.intensityrecords.app.steptrip.data.network.RemoteStepTripDataSource
import com.intensityrecords.app.steptrip.data.repository.DefaultStepTripRepository
import com.intensityrecords.app.steptrip.domain.StepTripRepository
import com.intensityrecords.app.steptrip.presentation.SelectedStepTripViewModel
import com.intensityrecords.app.steptrip.presentation.step_trip_detail_screen.StepTripsDetailScreenViewModel
import com.intensityrecords.app.steptrip.presentation.steptrip.StepTripScreenViewModel
import com.intensityrecords.app.workouts.data.network.KtorRemoteWorkoutDataSource
import com.intensityrecords.app.workouts.data.network.RemoteWorkoutDataSource
import com.intensityrecords.app.workouts.data.repository.DefaultWorkoutRepository
import com.intensityrecords.app.workouts.domain.WorkoutRepository
import com.intensityrecords.app.workouts.presentation.SelectedWorkOutViewModel
import com.intensityrecords.app.workouts.presentation.workouts_details_screen.WorkOutsDetailScreenViewModel
import com.intensityrecords.app.workouts.presentation.workouts_screen.WorkOutsScreenViewModel
import com.intensityrecords.app.login.presentation.login_screen.LoginScreenViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule = module {

    single { HttpClientFactory.create(get(), get()) }

    single { SessionProvider(get()) }

    singleOf(::KtorRemoteWorkoutDataSource).bind<RemoteWorkoutDataSource>()
    singleOf(::KtorRemoteStepTripDataSource).bind<RemoteStepTripDataSource>()
    singleOf(::KtorRemoteMobilityDataSource).bind<RemoteMobilityDataSource>()
    singleOf(::KtorRemoteAuthDataSource).bind<RemoteAuthDataSource>()
    singleOf(::KtorRemoteHomeDataSource).bind<RemoteHomeDataSource>()

    singleOf(::DefaultWorkoutRepository).bind<WorkoutRepository>()
    singleOf(::DefaultStepTripRepository).bind<StepTripRepository>()
    singleOf(::DefaultMobilityRepository).bind<MobilityRepository>()
    singleOf(::DefaultAuthRepository).bind<AuthRepository>()
    singleOf(::DefaultHomeRepository).bind<HomeRepository>()


//    single {
//        HttpClient {
//            install(ContentNegotiation) {
//                json(
//                    Json {
//                        ignoreUnknownKeys = true
//                    }
//                )
//            }
//
//            install(Logging) {
//                logger = Logger.DEFAULT
//                level = LogLevel.ALL
//            }
//        }
//    }


    viewModelOf(::HomeScreenViewModel)
    viewModelOf(::LiveScreenViewModel)
    viewModelOf(::MobilityScreenViewModel)
    viewModelOf(::WorkOutsScreenViewModel)
    viewModelOf(::WorkOutsDetailScreenViewModel)
    viewModelOf(::StepTripScreenViewModel)
    viewModelOf(::StepTripsDetailScreenViewModel)
    viewModelOf(::LanguageViewModel)
    viewModelOf(::SelectedWorkOutViewModel)
    viewModelOf(::SelectedStepTripViewModel)
    viewModelOf(::LoginScreenViewModel)
}
