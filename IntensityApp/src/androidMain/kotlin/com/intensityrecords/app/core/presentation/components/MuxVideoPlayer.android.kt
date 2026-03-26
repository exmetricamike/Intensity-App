package com.intensityrecords.app.core.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.Player
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.exoplayer.DefaultLoadControl
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import android.util.Log
import com.mux.player.MuxPlayer
import com.mux.player.media.MediaItems

//@Composable
//actual fun MuxVideoPlayer(
//    modifier: Modifier,
//    playbackId: String
//) {
//    val context = LocalContext.current
//
//    // Automatically format the Mux URL if just a playback ID is passed
//    val videoUrl = if (playbackId.startsWith("http")) playbackId else "https://test-streams.mux.dev/x36xhzz/x36xhzz.m3u8"
//
//    // Initialize ExoPlayer
//    val exoPlayer = remember {
//        ExoPlayer.Builder(context).build().apply {
//            val mediaItem = MediaItem.fromUri(videoUrl)
//            setMediaItem(mediaItem)
//            prepare()
//            playWhenReady = true // Auto-play
//        }
//    }
//
//    // Wrap the native PlayerView in Compose
//    AndroidView(
//        modifier = modifier,
//        factory = {
//            PlayerView(context).apply {
//                player = exoPlayer
//                useController = true // Show native play/pause controls
//            }
//        }
//    )
//
//    // Clean up the player when the composable leaves the screen
//    DisposableEffect(Unit) {
//        onDispose {
//            exoPlayer.release()
//        }
//    }
//}


//@Composable
//actual fun MuxVideoPlayer(
//    modifier: Modifier,
//    playbackId: String
//) {
//    val context = LocalContext.current
//
//    val player: MuxPlayer = MuxPlayer.Builder(context = context)
//        .enableLogcat(true) // Optional. Only applies to Mux. Media3 logging is not touched
//        .enableSmartCache(true)
//        .applyExoConfig {
//            setHandleAudioBecomingNoisy(true)
//        }.build()
//
//    val mediaItem = MediaItems.builderFromMuxPlaybackId(playbackId)
//        .setMediaMetadata(
//            MediaMetadata.Builder()
//                .setTitle("Hello from Mux Player on Android!")
//                .build()
//        )
//        .build()
//
//    player.setMediaItem(mediaItem)
//    player.prepare()
//    player.playWhenReady = true
//
//}


@Composable
actual fun MuxVideoPlayer(
    modifier: Modifier,
    playbackId: String
) {
    val context = LocalContext.current
    val enableSmartCache = false

    val player = remember {
        MuxPlayer.Builder(context)
            .enableLogcat(true)
            .enableSmartCache(enableSmartCache)
            .applyExoConfig {
                setHandleAudioBecomingNoisy(true)
                setLoadControl(
                    DefaultLoadControl.Builder()
                        .setBufferDurationsMs(
                            /* minBufferMs = */ 15_000,
                            /* maxBufferMs = */ 60_000,
                            /* bufferForPlaybackMs = */ 2_500,
                            /* bufferForPlaybackAfterRebufferMs = */ 5_000
                        )
                        .setPrioritizeTimeOverSizeThresholds(true)
                        .build()
                )
            }
            .build()
    }

    DisposableEffect(player) {
        val listener = object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                val state = when (playbackState) {
                    Player.STATE_IDLE -> "IDLE"
                    Player.STATE_BUFFERING -> "BUFFERING"
                    Player.STATE_READY -> "READY"
                    Player.STATE_ENDED -> "ENDED"
                    else -> "UNKNOWN"
                }
                Log.d("MuxVideoPlayer", "state=$state playbackId=$playbackId")
            }

            override fun onIsLoadingChanged(isLoading: Boolean) {
                Log.d("MuxVideoPlayer", "loading=$isLoading playbackId=$playbackId")
            }

            override fun onPlayerError(error: androidx.media3.common.PlaybackException) {
                Log.e("MuxVideoPlayer", "error playbackId=$playbackId code=${error.errorCodeName}", error)
            }
        }
        player.addListener(listener)
        onDispose {
            player.removeListener(listener)
            player.release()
        }
    }

    val mediaItem = remember(playbackId) {
        MediaItems.builderFromMuxPlaybackId(playbackId)
            .setMediaMetadata(
                MediaMetadata.Builder()
                    .setTitle("Mux Video")
                    .build()
            ).build()
    }

    AndroidView(
        modifier = modifier,
        factory = {
            PlayerView(context).apply {
                this.player = player
                useController = true
            }
        }
    )

    LaunchedEffect(mediaItem) {
        player.setMediaItem(mediaItem)
        player.prepare()
        player.playWhenReady = true
    }
}
