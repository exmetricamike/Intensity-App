package com.intensityrecord.sensor.presentation.dashboard

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

actual fun currentTimeMillis(): Long = System.currentTimeMillis()

actual fun formatCurrentTime(): String {
    val sdf = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    return sdf.format(Date())
}
