package com.intensityrecords.app.di

import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module
    get() = module {
        single<HttpClientEngine> { OkHttp.create() }
        single {
            PreferenceDataStoreFactory.create(
                produceFile = { androidContext().filesDir.resolve("prefs.preferences_pb") }
            )
        }
    }