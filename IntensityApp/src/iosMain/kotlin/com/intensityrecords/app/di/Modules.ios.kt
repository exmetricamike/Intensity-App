package com.intensityrecords.app.di

import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import kotlinx.cinterop.ExperimentalForeignApi
import okio.Path.Companion.toPath
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

@OptIn(ExperimentalForeignApi::class)
actual val platformModule: Module
    get() = module {
        single<HttpClientEngine> { Darwin.create() }

        single {
            val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
                directory = NSDocumentDirectory,
                inDomain = NSUserDomainMask,
                appropriateForURL = null,
                create = false,
                error = null,
            )
            val path = requireNotNull(documentDirectory?.path) + "/prefs.preferences_pb"

            PreferenceDataStoreFactory.createWithPath(
                produceFile = { path.toPath() }
            )
        }
    }