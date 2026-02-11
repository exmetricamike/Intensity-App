package com.intensityrecord.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.intensityrecord.auth.SettingsTokenStorage
import com.intensityrecord.auth.TokenStorage
import com.intensityrecord.auth.presentation.LoginViewModel
import com.intensityrecord.book.data.database.DatabaseFactory
import com.intensityrecord.book.data.database.FavoriteBookDatabase
import com.intensityrecord.book.data.network.KtorRemoteBookDataSource
import com.intensityrecord.book.data.network.RemoteBookDataSource
import com.intensityrecord.book.data.repository.DefaultBookRepository
import com.intensityrecord.book.domain.BookRepository
import com.intensityrecord.book.presentation.SelectedBookViewModel
import com.intensityrecord.book.presentation.book_detail.BookDetailViewModel
import com.intensityrecord.book.presentation.book_list.BookListViewModel
import com.intensityrecord.core.data.HttpClientFactory
import com.intensityrecord.core.data.ZensiPreferences
import com.intensityrecord.sensor.data.network.KtorZensiApi
import com.intensityrecord.sensor.data.network.ZensiApi
import com.intensityrecord.sensor.data.repository.DefaultSensorRepository
import com.intensityrecord.sensor.domain.SensorRepository
import com.intensityrecord.sensor.presentation.app_settings.AppSettingsViewModel
import com.intensityrecord.sensor.presentation.calibrate.CalibrateViewModel
import com.intensityrecord.sensor.presentation.chart.ChartViewModel
import com.intensityrecord.sensor.presentation.dashboard.DashboardViewModel
import com.intensityrecord.sensor.presentation.demo.DemoViewModel
import com.intensityrecord.sensor.presentation.sensor_settings.SensorSettingsViewModel
import com.intensityrecords.app.home.presentation.home_screen.HomeScreenViewModel
import com.intensityrecords.app.live.presentation.live_screen.LiveScreenViewModel
import com.intensityrecords.app.mobility.presentation.mobility_screen.MobilityScreenViewModel
import com.intensityrecords.app.workouts.presentation.workouts_details_screen.WorkOutsDetailScreenViewModel
import com.intensityrecords.app.workouts.presentation.workouts_screen.WorkOutsScreenViewModel
import com.russhwolf.settings.Settings
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule = module {
    // --- Database and Core dependencies ---
    single { get<DatabaseFactory>().create().setDriver(BundledSQLiteDriver()).build() }

    // --- Settings / TokenStorage ---
    single { Settings() }
    singleOf(::SettingsTokenStorage).bind<TokenStorage>()

    // --- Zensi Preferences ---
    single { ZensiPreferences(get()) }

    // --- HTTP Client ---
    single<HttpClient> {
        val engine: HttpClientEngine = get()
        val zensiPreferences: ZensiPreferences = get()

        HttpClientFactory.create(
            engine = engine,
            tokenProvider = { zensiPreferences.token }
        )
    }

    // --- Zensi Network ---
    single<ZensiApi> { KtorZensiApi(get()) }
    single<SensorRepository> { DefaultSensorRepository(get()) }

    // --- Zensi ViewModels ---
    viewModel { LoginViewModel(get(), get()) }
    viewModelOf(::DashboardViewModel)
    viewModel { params -> SensorSettingsViewModel(params.get(), get(), get()) }
    viewModel { params -> CalibrateViewModel(params.get(), get()) }
    viewModel { params -> ChartViewModel(params.get(), get()) }
    viewModelOf(::AppSettingsViewModel)
    viewModelOf(::DemoViewModel)

    // --- Books feature ---
    single { get<FavoriteBookDatabase>().favoriteBookDao }
    singleOf(::KtorRemoteBookDataSource).bind<RemoteBookDataSource>()
    singleOf(::DefaultBookRepository).bind<BookRepository>()

    viewModelOf(::BookListViewModel)
    viewModelOf(::BookDetailViewModel)
    viewModelOf(::SelectedBookViewModel)

    viewModelOf(::HomeScreenViewModel)
    viewModelOf(::LiveScreenViewModel)
    viewModelOf(::MobilityScreenViewModel)
    viewModelOf(::WorkOutsScreenViewModel)
    viewModelOf(::WorkOutsDetailScreenViewModel)
}
