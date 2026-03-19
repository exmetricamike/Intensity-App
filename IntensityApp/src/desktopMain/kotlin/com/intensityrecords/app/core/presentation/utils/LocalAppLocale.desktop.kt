package com.intensityrecords.app.core.presentation.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidedValue
import androidx.compose.runtime.staticCompositionLocalOf
import java.util.Locale

actual object LocalAppLocale {

    private var defaultLocale: Locale? = null

    private val LocalDesktopAppLocale = staticCompositionLocalOf { getDefaultLocale() }

    actual val current: String
        @Composable
        get() = LocalDesktopAppLocale.current

    @Composable
    actual infix fun provides(value: String?): ProvidedValue<*> {
        if (defaultLocale == null) {
            defaultLocale = Locale.getDefault()
        }

        val newLocale = if (value == null) {
            defaultLocale!!
        } else {
            // Using forLanguageTag allows it to parse standard standard BCP 47 tags (e.g., "en-US")
            Locale.forLanguageTag(value.replace("_", "-"))
        }

        // Set the JVM-wide locale
        Locale.setDefault(newLocale)

        // Provide the value to the Compose tree
        return LocalDesktopAppLocale provides newLocale.toString()
    }
}