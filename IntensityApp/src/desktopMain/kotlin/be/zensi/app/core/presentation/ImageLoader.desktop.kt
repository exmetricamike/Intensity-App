package com.intensityrecord.core.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.res.useResource
import java.awt.image.BufferedImage
import javax.imageio.ImageIO

@Composable
actual fun loadImage(name: String): Painter {
    val stream = useResource(name) { it }
    val bufferedImage: BufferedImage = ImageIO.read(stream)
    val bitmap: ImageBitmap = bufferedImage.toComposeImageBitmap()
    return BitmapPainter(bitmap)
}
