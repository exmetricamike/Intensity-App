package com.intensityrecord

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.intensityrecords.app.app.App
import com.intensityrecord.di.initKoin

fun main() {
    initKoin()
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "Zensi Kmp App",
        ) {
            //App(engine = remember { OkHttp.create() })
            App()
        }
    }
}