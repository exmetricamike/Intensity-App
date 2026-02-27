package com.intensityrecord

import androidx.compose.ui.window.ComposeUIViewController
import com.intensityrecords.app.app.App

//fun MainViewController() = ComposeUIViewController { App(engine = remember { Darwin.create() }) }

import com.intensityrecords.app.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) { App() }