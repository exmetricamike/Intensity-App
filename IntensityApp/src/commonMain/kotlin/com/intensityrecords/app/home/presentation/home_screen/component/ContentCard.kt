package com.intensityrecords.app.home.presentation.home_screen.component

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import coil3.compose.SubcomposeAsyncImage
import com.intensityrecords.app.app.Route
import com.intensityrecord.core.presentation.CardBackground
import com.intensityrecord.core.presentation.GlowBorderBrush
import com.intensityrecord.core.presentation.PrimaryAccent
import com.intensityrecords.app.core.domain.AppDimens
import intensityrecordapp.intensityapp.generated.resources.Res
import intensityrecordapp.intensityapp.generated.resources.step_trip
import intensityrecordapp.intensityapp.generated.resources.mobility_home_card
import intensityrecordapp.intensityapp.generated.resources.live_class
import intensityrecordapp.intensityapp.generated.resources.workout
import intensityrecordapp.intensityapp.generated.resources.live_tag
import com.intensityrecords.app.core.presentation.utils.LocalAppLocale
import com.intensityrecords.app.home.domain.UiBlock
import com.intensityrecords.app.home.domain.UiConfig
import com.intensityrecords.app.home.domain.title
import com.intensityrecords.app.workouts.presentation.workouts_screen.component.pulseAnimation
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun ContentCard(
    item: UiBlock,
    width: Dp,
    aspectRatio: Float,
    navController: NavController,
    dimens: AppDimens,
    isWideScreen: Boolean,
    modifier: Modifier = Modifier
) {

    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    val isHovered by interactionSource.collectIsHoveredAsState()
    val isActive = isFocused || isHovered

    val scale by animateFloatAsState(
        targetValue = if (isFocused) 1.20f else 1f,
        animationSpec = tween(300),
        label = "scale"
    )

    val shadowElevation by animateDpAsState(
        targetValue = if (isFocused) 12.dp else 0.dp,
        animationSpec = tween(300),
        label = "elevation"
    )

    val borderWidth = if (isFocused) dimens.borderWidthActive else dimens.borderWidthNormal

    val borderBrush = if (isActive) {
        Brush.horizontalGradient(
            colorStops = arrayOf(
                0.0f to PrimaryAccent.copy(alpha = 0.4f),
                0.2f to PrimaryAccent.copy(alpha = 0.8f),
                0.5f to PrimaryAccent,           // Center Bright Core
                0.8f to PrimaryAccent.copy(alpha = 0.8f),
                1.0f to PrimaryAccent.copy(alpha = 0.4f)
            )
        )
    } else {
        GlowBorderBrush
    }

    // 1. Create the requester and scope
    val bringIntoViewRequester = remember { BringIntoViewRequester() }

    // 2. Track the size of the card so we know how much to expand
    var cardSize by remember { mutableStateOf(IntSize.Zero) }

    // 3. Trigger the scroll request when focus changes
    LaunchedEffect(isFocused) {
        if (isFocused) {
            // We calculate a rect that is taller than the actual card.
            // This forces the grid to scroll up enough to show the "phantom" bottom area.
            val size = cardSize.toSize()
            val extraBottomSpace = size.height * 0.2f // Add 20% buffer to the bottom

            val expandedRect = Rect(
                left = 0f,
                top = 0f,
                right = size.width,
                bottom = size.height + extraBottomSpace
            )

            bringIntoViewRequester.bringIntoView(expandedRect)
        }
    }

    val locale = LocalAppLocale.current

    val mobility = stringResource(Res.string.mobility_home_card)
    val live = stringResource(Res.string.live_class)
    val workouts = stringResource(Res.string.workout)
    val stepTrip = stringResource(Res.string.step_trip)

    Card(
        modifier = modifier
            .zIndex(if (isActive) 1f else 0f)
            .width(width)
            .aspectRatio(aspectRatio)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .onSizeChanged { cardSize = it }
            // Attach the requester (MUST be before clickable/focusable)
            .bringIntoViewRequester(bringIntoViewRequester)
            .shadow(
                elevation = shadowElevation,
                shape = RoundedCornerShape(dimens.cardCornerRadius),
                spotColor = PrimaryAccent,
                ambientColor = PrimaryAccent
            )
            .clip(RoundedCornerShape(dimens.cardCornerRadius))
            .border(
                BorderStroke(borderWidth, borderBrush),
                RoundedCornerShape(dimens.cardCornerRadius)
            )
            .clickable(interactionSource = interactionSource, indication = null) {
//                when (item.title) {
//                    workouts -> {
//                        navController.navigate(Route.WorkOuts)
//                    }
//
//                    mobility -> {
//                        navController.navigate(Route.Mobility)
//                    }
//
//                    live -> {
//                        navController.navigate(Route.Live)
//                    }
//
//                    stepTrip -> {
//                        if (!isWideScreen) navController.navigate(Route.StepTrip)
//                    }
//                }
                when (item.url) {

                    "/workout" -> navController.navigate(Route.WorkOuts)

                    "/program" -> navController.navigate(Route.Programs)

                    "/live" -> navController.navigate(Route.Live)

                    "/dailyvideo" -> navController.navigate(Route.VideoDetail)

                    "steptrip" -> {
                        if (!isWideScreen) navController.navigate(Route.StepTrip)
                    }

//                    "/program" -> navController.navigate(Route.Mobility)

                }

            },
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.fillMaxSize().background(Color(0xFF1A1A1A))) {
//                Image(
//                    painter = painterResource(resource = item.icon),
//                    contentDescription = null,
//                    contentScale = ContentScale.Crop,
//                    modifier = Modifier.fillMaxSize()
//                )
                SubcomposeAsyncImage(
                    model = item.imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    alignment = if (isWideScreen) Alignment.Center else Alignment.TopCenter,
                    modifier = Modifier.fillMaxSize(),
                    loading = {
                        // This box will show the shimmer animation until the image is ready
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .pulseAnimation()
                        )
                    },
                    error = {
                        // Optional: Show a specific icon if the image fails
                        Box(
                            modifier = Modifier.fillMaxSize().background(Color.DarkGray),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Default.Warning,
                                contentDescription = null,
                                tint = Color.White
                            )
                        }
                    }
                )
            }
            Box(
                modifier = Modifier.fillMaxSize().background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.8f)
                        )
                    )
                )
            )
            Text(
                text = (item.title(locale) ?: "").uppercase(),
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = dimens.cardTitle),
                modifier = Modifier.align(if (isWideScreen) Alignment.Center else Alignment.Center).padding(16.dp)
            )
            if (item.url == "/live") {
                Box(
                    modifier = Modifier.align(Alignment.TopStart).padding(12.dp)
                        .background(PrimaryAccent, RoundedCornerShape(4.dp))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        stringResource(Res.string.live_tag),
                        style = MaterialTheme.typography.labelSmall,
                    )
                }
            }
        }
    }
}
