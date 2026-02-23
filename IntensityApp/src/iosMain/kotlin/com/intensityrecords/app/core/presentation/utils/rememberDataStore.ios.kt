package com.intensityrecords.app.core.presentation.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

@Composable
actual fun rememberDataStore(): DataStore<Preferences> {
    return remember { createDataStore() }
}