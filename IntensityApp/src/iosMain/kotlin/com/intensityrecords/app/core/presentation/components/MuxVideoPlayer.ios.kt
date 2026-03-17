package com.intensityrecords.app.core.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitViewController
import kotlinx.cinterop.ExperimentalForeignApi
import platform.AVFoundation.AVPlayer
import platform.AVFoundation.pause
import platform.AVFoundation.play
import platform.AVKit.AVPlayerViewController
import platform.Foundation.NSURL

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun MuxVideoPlayer(
    modifier: Modifier,
    playbackId: String
) {
    val videoUrl = if (playbackId.startsWith("http")) playbackId else "https://stream.mux.com/$playbackId.m3u8"

    // Initialize Apple's AVPlayer
    val player = remember {
        NSURL.URLWithString(videoUrl)?.let { AVPlayer(uRL = it) }
    }

    if (player != null) {
        // Wrap the native iOS View Controller in Compose
        UIKitViewController(
            modifier = modifier,
            factory = {
                val playerViewController = AVPlayerViewController()
                playerViewController.player = player
                playerViewController.showsPlaybackControls = true // Show Apple native controls
                playerViewController
            },
            update = {
                // You can update the view controller here if state changes
            }
        )

        // Handle play/pause lifecycle
        DisposableEffect(Unit) {
            player.play() // Auto-play
            onDispose {
                player.pause() // Stop audio when leaving screen
            }
        }
    }
}