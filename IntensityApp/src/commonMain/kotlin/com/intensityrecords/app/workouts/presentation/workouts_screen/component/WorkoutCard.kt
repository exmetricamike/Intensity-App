package com.intensityrecords.app.workouts.presentation.workouts_screen.component

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowCircleRight
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.KeyboardDoubleArrowRight
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Timer
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import coil3.compose.SubcomposeAsyncImage
import com.intensityrecord.core.presentation.CardBackground
import com.intensityrecord.core.presentation.GlowBorderBrush
import com.intensityrecord.core.presentation.PrimaryAccent
import com.intensityrecords.app.core.domain.AppDimens
import com.intensityrecords.app.workouts.domain.WorkoutCollection
import com.intensityrecords.app.workouts.domain.WorkoutItem
import com.intensityrecords.app.workouts.domain.WorkoutSection
import com.intensityrecords.app.workouts.presentation.workouts_details_screen.component.StatBadge
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource

@Composable
fun WorkoutCard(
    item: WorkoutCollection,
    modifier: Modifier = Modifier,
    isWideScreen: Boolean,
    onClick: () -> Unit,
    dimens: AppDimens
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    val isHovered by interactionSource.collectIsHoveredAsState()
    val isActive = isFocused || isHovered

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
            delay(100)
            bringIntoViewRequester.bringIntoView(expandedRect)
        }
    }

    val scale by animateFloatAsState(
        targetValue = if (isFocused) 1.08f else 1f,
        animationSpec = tween(300),
        label = "scale"
    )

    val shadowElevation by animateDpAsState(
        targetValue = if (isFocused) 6.dp else 0.dp,
        animationSpec = tween(300),
        label = "elevation"
    )

    val borderWidth = if (isFocused) dimens.borderWidthActive else dimens.borderWidthNormal

    val badgeTextSize = if (isWideScreen) 14.sp else 13.sp
    val badgeIconSize = if (isWideScreen) 20.dp else 15.dp
    val badgeInternalSpacing = if (isWideScreen) 8.dp else 4.dp
    val badgeGroupSpacing = if (isWideScreen) 10.dp else 6.dp
    val surfaceHeight = if (isWideScreen) 45.dp else 30.dp

    val borderBrush = if (isActive) {
        Brush.horizontalGradient(
            colorStops = arrayOf(
                0.0f to Color.Transparent,
                0.3f to Color.Transparent,       // Start fading in
                0.45f to PrimaryAccent.copy(alpha = 0.5f), // Outer Glow
                0.5f to PrimaryAccent,           // Center Bright Core
                0.55f to PrimaryAccent.copy(alpha = 0.5f), // Outer Glow
                0.7f to Color.Transparent,       // Fade out
                1.0f to Color.Transparent
            )
        )
    } else {
        GlowBorderBrush
    }

    val aspectRatioForCard = if (isWideScreen) 1.2f else 2.2f

    Card(
        modifier = modifier
            .aspectRatio(aspectRatioForCard)
            .onSizeChanged { cardSize = it }
            // Attach the requester (MUST be before clickable/focusable)
            .bringIntoViewRequester(bringIntoViewRequester)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .shadow(
                elevation = shadowElevation,
                shape = RoundedCornerShape(dimens.cardCornerRadius),
                spotColor = PrimaryAccent,
                ambientColor = PrimaryAccent
            )
            .clip(RoundedCornerShape(16.dp))
            .border(BorderStroke(borderWidth, borderBrush), RoundedCornerShape(16.dp))
            .clickable(interactionSource = interactionSource, indication = null, onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
                .background(Color.Black.copy(alpha = 0.6f))
        ) {

            if (!isWideScreen) {
                Row(modifier = Modifier.fillMaxSize())
                {
                    Spacer(modifier = Modifier.weight(1f))
                    Box(modifier = Modifier.weight(2.0f).fillMaxHeight()) {
//                        AsyncImage(
////                            painter = painterResource(item.imageUrl),
////                            contentDescription = null,
////                            contentScale = ContentScale.Crop,
////                            modifier = Modifier.fillMaxSize()
//                            model = item.imageUrl,
//                            contentDescription = null,
//                            contentScale = ContentScale.Crop,
//                            modifier = Modifier.fillMaxSize()
//                        )
                        SubcomposeAsyncImage(
                            model = item.coverImage,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
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
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.horizontalGradient(
                                        colors = listOf(Color.Black, Color.Transparent),
                                        startX = 0f,
                                        endX = 500f
                                    )
                                )
                        )
                    }
                }
            } else {
//                AsyncImage(
////                    painter = painterResource(item.image),
////                    contentDescription = item.title,
////                    contentScale = ContentScale.Crop,
////                    modifier = Modifier.fillMaxSize().alpha(0.7f)
//                    model = item.imageUrl,
//                    contentDescription = null,
//                    contentScale = ContentScale.Crop,
//                    modifier = Modifier.fillMaxSize().alpha(0.7f)
//                )
                SubcomposeAsyncImage(
                    model = item.coverImage,
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxHeight()
                        .wrapContentWidth()
                        .align(Alignment.CenterEnd),
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

            // Gradient Overlay (Scrim) - Crucial for text readability
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Black.copy(alpha = 0.3f), // Top slightly dark
                                Color.Transparent,              // Middle clear
                                Color.Black.copy(alpha = 0.9f)  // Bottom dark for text
                            )
                        )
                    )
            )

            Text(
                text = item.name,
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = if (isWideScreen) 26.sp else 21.sp),
                textAlign = if (isWideScreen) TextAlign.Center else TextAlign.Start, // Handles multi-line titles gracefully
                modifier = Modifier
                    .fillMaxWidth()
                    .align(if (isWideScreen) Alignment.Center else Alignment.TopCenter) // Places text in the dead center
                    .padding(
                        horizontal = if (isWideScreen) 16.dp else 15.dp,
                        vertical = if (isWideScreen) 0.dp else 55.dp
                    ) // Prevents text touching edges
            )

            Spacer(modifier = Modifier.height(14.dp))

            if (!isWideScreen) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            end = 15.dp,
                            top = if (isWideScreen) 0.dp else 65.dp
                        )
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = null,
                        tint = PrimaryAccent,
                        modifier = Modifier.size(40.dp)
                    )
                }
            }

//            Surface(
//                color = PrimaryAccent.copy(alpha = 0.2f),
//                shape = RoundedCornerShape(50),
//                border = BorderStroke(1.dp, PrimaryAccent),
//                modifier = Modifier
//                    .align(Alignment.BottomCenter)
//                    .padding(bottom = 12.dp, start = 8.dp, end = 8.dp)
//                    .height(surfaceHeight)
//                    .wrapContentWidth()
//            ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = if (isWideScreen) Arrangement.Center else Arrangement.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        bottom = 12.dp,
                        start = if (isWideScreen) 8.dp else 15.dp,
                        end = 8.dp,
                        top = if (isWideScreen) 0.dp else 45.dp
                    ).align(if (isWideScreen) Alignment.BottomCenter else Alignment.Center)
                    .height(surfaceHeight)
            ) {

                StatBadge(
                    icon = Icons.Default.Timer,
                    text = "15 MIN",
                    iconSize = badgeIconSize,
                    textStyle = MaterialTheme.typography.displaySmall.copy(fontSize = badgeTextSize),
                    spacing = badgeInternalSpacing
                )

//                    Spacer(modifier = Modifier.width(badgeGroupSpacing))

//                    Box(
//                        modifier = Modifier
//                            .width(1.dp)
//                            .height(12.dp)
//                            .background(PrimaryAccent.copy(0.5f))
//                    )
//
                Spacer(modifier = Modifier.width(badgeGroupSpacing))

                StatBadge(
                    icon = Icons.Default.LocalFireDepartment,
                    text = "180 KCAL",
                    iconSize = badgeIconSize,
                    textStyle = MaterialTheme.typography.displaySmall.copy(fontSize = badgeTextSize),
                    spacing = badgeInternalSpacing
                )
            }
//            }
        }
    }
}


@Composable
fun Modifier.pulseAnimation(): Modifier {
    val transition = rememberInfiniteTransition()
    val progress by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Restart
        )
    )
    return this.then(
        Modifier
            .graphicsLayer {
                scaleX = progress
                scaleY = progress
                alpha = 1f - progress
            }
            .border(
                width = 5.dp,
                color = PrimaryAccent,
                shape = CircleShape
            )
    )

}