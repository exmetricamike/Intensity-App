package com.testdroid.sample.androidapp

import android.app.Application
import com.intensityrecords.app.di.initKoin
import org.koin.android.ext.koin.androidContext

class IntensityApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@IntensityApplication)
        }
    }
}