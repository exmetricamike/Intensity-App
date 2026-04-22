package com.intensityrecords.app.home.presentation.home_screen.component

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CenterFocusStrong
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import coil3.compose.SubcomposeAsyncImage
import com.intensityrecord.core.presentation.CardBackground
import com.intensityrecord.core.presentation.GlowBorderBrush
import com.intensityrecord.core.presentation.PrimaryAccent
import com.intensityrecords.app.core.presentation.LocalAppDimens
import com.intensityrecords.app.core.presentation.components.MuxVideoPlayer
import com.intensityrecords.app.core.presentation.utils.LocalAppLocale
import com.intensityrecords.app.home.domain.DailyVideo
import com.intensityrecords.app.home.domain.focusLabel
import com.intensityrecords.app.home.domain.muxId
import com.intensityrecords.app.home.domain.tagline
import com.intensityrecords.app.home.domain.title
import com.intensityrecords.app.home.presentation.home_screen.DailyVideoUiState
import com.intensityrecords.app.workouts.presentation.workouts_details_screen.component.StatBadge
import com.intensityrecords.app.workouts.presentation.workouts_screen.component.pulseAnimation
import intensityrecordapp.intensityapp.generated.resources.Res
import intensityrecordapp.intensityapp.generated.resources.start_now
import org.jetbrains.compose.resources.stringResource

@Composable
fun VideoOfTheDayCard(
    navController: NavController,
    isWideScreen: Boolean,
    dynamicHeight: Dp,
    modifier: Modifier = Modifier,
    dailyVideoState: DailyVideoUiState,
) {
    val dimens = LocalAppDimens.current

    var showFullScreenVideo by remember { mutableStateOf(false) }

    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val isHovered by interactionSource.collectIsHoveredAsState()
    val isActive = isFocused || isHovered

    val scale by animateFloatAsState(
        targetValue = if (isFocused) 1.10f else 1f,
        animationSpec = tween(300),
        label = "scale"
    )

    val shadowElevation by animateDpAsState(
        targetValue = if (isFocused) 14.dp else 0.dp,
        animationSpec = tween(300),
        label = "elevation"
    )

    val borderWidth = if (isFocused) dimens.borderActive else dimens.borderNormal
    val buttonElevation by animateDpAsState(if (isFocused) 22.dp else 0.dp)

    val borderBrush = if (isActive) {
        Brush.horizontalGradient(
            colorStops = arrayOf(
                0.0f to PrimaryAccent.copy(alpha = 0.4f),
                0.2f to PrimaryAccent.copy(alpha = 0.8f),
                0.5f to PrimaryAccent,
                0.8f to PrimaryAccent.copy(alpha = 0.8f),
                1.0f to PrimaryAccent.copy(alpha = 0.4f)
            )
        )
    } else {
        GlowBorderBrush
    }

    val isClickable = dailyVideoState is DailyVideoUiState.Available

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(dimens.videoCardHeight)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .shadow(
                elevation = shadowElevation,
                shape = RoundedCornerShape(dimens.cornerRadius),
                spotColor = PrimaryAccent,
                ambientColor = PrimaryAccent,
            )
            .clip(RoundedCornerShape(dimens.cornerRadius))
            .background(CardBackground)
            .border(BorderStroke(borderWidth, borderBrush), RoundedCornerShape(dimens.cornerRadius))
            .then(
                if (isClickable) Modifier.clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) { showFullScreenVideo = true }
                else Modifier
            )
    ) {
        when (dailyVideoState) {
            is DailyVideoUiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize().pulseAnimation())
            }

            is DailyVideoUiState.Available -> {
                VideoOfTheDayContent(
                    video = dailyVideoState.video,
                    isWideScreen = isWideScreen,
                    buttonElevation = buttonElevation,
                    onPlay = { showFullScreenVideo = true }
                )
            }

            is DailyVideoUiState.NotAvailable -> {
                Box(
                    modifier = Modifier.fillMaxSize().background(Color(0xFF222222)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No video available yet",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White.copy(alpha = 0.6f)
                    )
                }
            }

            is DailyVideoUiState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize().background(Color(0xFF222222)),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            Icons.Default.Refresh,
                            contentDescription = null,
                            tint = Color.White.copy(alpha = 0.6f),
                            modifier = Modifier.size(32.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Could not load video",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White.copy(alpha = 0.6f)
                        )
                    }
                }
            }
        }
    }

    if (showFullScreenVideo && dailyVideoState is DailyVideoUiState.Available) {
        val locale = LocalAppLocale.current
        val muxId = dailyVideoState.video.muxId(locale)

        val closeFocusRequester = remember { FocusRequester() }
        val closeInteractionSource = remember { MutableInteractionSource() }
        val isCloseFocused by closeInteractionSource.collectIsFocusedAsState()

        val closeScale by animateFloatAsState(if (isCloseFocused) 1.2f else 1f)
        val closeStrokeWidth by animateDpAsState(if (isCloseFocused) 3.dp else 0.dp)

        Dialog(
            onDismissRequest = { showFullScreenVideo = false },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
            ) {
                if (muxId != null) {
                    MuxVideoPlayer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxSize()
                            .aspectRatio(16f / 9f),
                        playbackId = muxId
                    )
                } else {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No video available for your language",
                            color = Color.White
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(24.dp)
                        .graphicsLayer {
                            scaleX = closeScale
                            scaleY = closeScale
                        }
                        .size(56.dp)
                        .shadow(
                            elevation = if (isCloseFocused) 10.dp else 0.dp,
                            shape = CircleShape,
                            spotColor = PrimaryAccent
                        )
                        .focusRequester(closeFocusRequester)
                        .background(
                            color = if (isCloseFocused) Color.Black else Color.Black.copy(alpha = 0.5f),
                            shape = CircleShape
                        )
                        .border(
                            width = closeStrokeWidth,
                            color = PrimaryAccent,
                            shape = CircleShape
                        )
                        .clickable(
                            interactionSource = closeInteractionSource,
                            indication = null
                        ) { showFullScreenVideo = false },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close Video",
                        tint = if (isCloseFocused) PrimaryAccent else Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun VideoOfTheDayContent(
    video: DailyVideo,
    isWideScreen: Boolean,
    buttonElevation: Dp,
    onPlay: () -> Unit,
) {
    val dimens = LocalAppDimens.current
    val locale = LocalAppLocale.current

    Box(modifier = Modifier.fillMaxSize().background(Color(0xFF222222))) {
        SubcomposeAsyncImage(
            model = video.coverImage,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            alignment = Alignment.CenterEnd,
            modifier = Modifier.fillMaxSize(),
            loading = {
                Box(modifier = Modifier.fillMaxSize().pulseAnimation())
            },
            error = {
                Box(
                    modifier = Modifier.fillMaxSize().background(Color.DarkGray),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Warning, contentDescription = null, tint = Color.White)
                }
            }
        )

        // Gradient overlay
        Box(
            modifier = Modifier.fillMaxSize().background(
                Brush.horizontalGradient(
                    colors = listOf(Color.Black.copy(alpha = 0.9f), Color.Transparent),
                    startX = 0f, endX = 1500f
                )
            )
        )

        // Text content
        Column(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(dimens.paddingLarge)
                .fillMaxWidth(0.7f)
        ) {
        Text(
            text = video.title(locale) ?: "",
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = dimens.titleLarge)
        )
        val taglineText = video.tagline(locale)
        if (!taglineText.isNullOrBlank()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = taglineText,
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = dimens.bodyMedium)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = onPlay,
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryAccent),
            shape = RoundedCornerShape(24),
            modifier = Modifier.height(dimens.buttonHeight)
                .shadow(
                    elevation = buttonElevation,
                    shape = RoundedCornerShape(24),
                    spotColor = PrimaryAccent,
                    ambientColor = PrimaryAccent
                )
        ) {
            Text(
                text = stringResource(Res.string.start_now),
                style = MaterialTheme.typography.labelMedium.copy(
                    fontSize = dimens.buttonText,
                    color = Color.Black
                )
            )
        }
        Spacer(modifier = Modifier.height(10.dp))

        val durationText = video.durationLabelMin?.let { "$it min" }
        val calText = video.caloriesBurnedLabel?.let { "$it kcal" }
        val focusText = video.focusLabel(locale)

        if (isWideScreen) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (durationText != null) {
                    StatBadge(icon = Icons.Default.Timer, text = durationText)
                    Spacer(modifier = Modifier.width(24.dp))
                }
                if (calText != null) {
                    StatBadge(icon = Icons.Default.LocalFireDepartment, text = calText)
                    Spacer(modifier = Modifier.width(24.dp))
                }
                if (focusText != null) {
                    StatBadge(icon = Icons.Default.CenterFocusStrong, text = focusText)
                }
            }
        } else {
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (durationText != null) StatBadge(icon = Icons.Default.Timer, text = durationText)
                if (calText != null) StatBadge(icon = Icons.Default.LocalFireDepartment, text = calText)
                if (focusText != null) StatBadge(icon = Icons.Default.CenterFocusStrong, text = focusText)
            }
        }
    } // Column
    } // Box
}
