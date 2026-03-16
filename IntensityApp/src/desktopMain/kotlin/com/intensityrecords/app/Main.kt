package com.intensityrecords.app

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.intensityrecords.app.app.App
import com.intensityrecords.app.di.initKoin

fun main() {
    initKoin()
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "Intensity App",
            undecorated = false
        ) {
            App()
        }
    }
}