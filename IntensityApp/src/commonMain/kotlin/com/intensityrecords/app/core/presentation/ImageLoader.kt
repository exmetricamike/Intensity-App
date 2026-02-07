package com.intensityrecord.core.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter



/**
 * Loads an image as a Painter that can be used in Compose Image.
 * @param name filename of the resource (e.g., "logo.png" or "logo.svg")
 */
expect @Composable
fun loadImage(name: String): Painter

