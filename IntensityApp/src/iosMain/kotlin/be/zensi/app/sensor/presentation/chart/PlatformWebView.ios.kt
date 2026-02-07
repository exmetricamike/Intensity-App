package com.intensityrecord.sensor.presentation.chart

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import kotlinx.cinterop.ExperimentalForeignApi
import platform.WebKit.WKWebView

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun PlatformWebView(htmlContent: String, modifier: Modifier) {
    UIKitView(
        factory = {
            WKWebView().apply {
                loadHTMLString(htmlContent, baseURL = null)
            }
        },
        update = { webView ->
            webView.loadHTMLString(htmlContent, baseURL = null)
        },
        modifier = modifier
    )
}
