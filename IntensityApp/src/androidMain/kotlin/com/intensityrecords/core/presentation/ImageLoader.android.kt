package com.intensityrecord.core.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.res.vectorResource
import com.intensityrecord.R


@Composable
actual fun loadImage(name: String): Painter {
    val resId = when(name) {
        "logo.png" -> R.drawable.ic_launcher_background
        "ic_eye_open" -> R.drawable.ic_eye_open
        "ic_eye_closed" -> R.drawable.ic_eye_closed
//        "logo.svg" -> R.drawable.logo_vector // your VectorDrawable
        else -> throw IllegalArgumentException("Unknown resource: $name")
    }
    return painterResource(id = resId)
}

