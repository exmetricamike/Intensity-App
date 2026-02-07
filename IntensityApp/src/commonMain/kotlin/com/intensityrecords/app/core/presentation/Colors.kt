package com.intensityrecord.core.presentation

import androidx.compose.ui.graphics.Color

// Book feature colors (kept for reference)
val DarkBlue = Color(0xFF0B405E)
val DesertWhite = Color(0xFFF7F7F7)
val SandYellow = Color(0xFFFFBD64)
val LightBlue = Color(0xFF9AD9FF)

// Zensi brand colors
val ZensiPrimary = Color(0xFF00B0F0)         // colorPrimary from colors.xml
val ZensiPrimaryDark = Color(0xFF0078A8)     // darker variant for gradients

// Sensor status colors
val SensorOccupied = Color(0xFF4CAF50)       // green - bed is occupied
val SensorStandby = Color(0xFF9E9E9E)        // gray - sensor in standby
val SensorUndetected = Color(0xFFBDBDBD)     // light gray - sensor undetected

// Alert/triggered level colors
val SensorTriggeredRed = Color(0xFFE53935)   // red - immediate alert
val SensorTriggeredOrange = Color(0xFFFF9800) // orange - warning level
val SensorTriggeredBlue = Color(0xFF2196F3)   // blue - info level

// Alert time threshold colors (before-first, before-second, before-third, after-third)
val AlertBeforeFirst = Color(0xFF4CAF50)     // green - within first threshold
val AlertBeforeSecond = Color(0xFFFF9800)    // orange - approaching threshold
val AlertBeforeThird = Color(0xFFE53935)     // red - near limit
val AlertAfterThird = Color(0xFFB71C1C)      // dark red - past limit