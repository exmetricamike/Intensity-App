package com.intensityrecords.app.di

import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.core.module.Module
import org.koin.dsl.module
import java.io.File

actual val platformModule: Module
    get() = module {
        single<HttpClientEngine> { OkHttp.create() }

        single {
            PreferenceDataStoreFactory.create(
                produceFile = {
                    // Saves to: C:\Users\Username\prefs.preferences_pb (Windows)
                    // Or: /Users/Username/prefs.preferences_pb (Mac)
                    File(System.getProperty("user.home"), "prefs.preferences_pb")
                }
            )
        }
    }