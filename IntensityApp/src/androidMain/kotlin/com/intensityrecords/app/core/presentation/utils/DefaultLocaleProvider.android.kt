package com.intensityrecords.app.core.presentation.utils

import java.util.Locale

actual fun getDefaultLocale(): String {
    return Locale.getDefault().toString()
}