package com.intensityrecord.sensor.presentation.chart

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
actual fun PlatformWebView(htmlContent: String, modifier: Modifier) {
    // Desktop does not have a built-in WebView in Compose Desktop.
    // Display a placeholder message. A real implementation could use JCEF or JavaFX WebView.
    Text(
        text = "Chart preview is not available on desktop. Please use the mobile app.",
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    )
}
