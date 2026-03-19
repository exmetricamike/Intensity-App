package com.intensityrecords.app.core.presentation.utils

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import java.io.File

fun createDataStore(): DataStore<Preferences> {
    return createDataStore(
        producePath = {
            // Gets the OS user's home directory
            val homeDir = System.getProperty("user.home")

            // Creates a hidden folder for your app's data
            val appDir = File(homeDir, ".intensityrecords")
            if (!appDir.exists()) {
                appDir.mkdirs()
            }

            File(appDir, "prefs.preferences_pb").absolutePath
        }
    )
}