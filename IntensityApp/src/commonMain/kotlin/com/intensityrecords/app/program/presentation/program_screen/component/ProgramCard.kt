package com.intensityrecords.app.program.presentation.program_screen.component

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
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
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
import coil3.compose.SubcomposeAsyncImage
import com.intensityrecord.core.presentation.CardBackground
import com.intensityrecord.core.presentation.GlowBorderBrush
import com.intensityrecord.core.presentation.PrimaryAccent
import com.intensityrecords.app.core.domain.AppDimens
import com.intensityrecords.app.core.presentation.utils.LocalAppLocale
import com.intensityrecords.app.program.domain.ProgramCollection
import com.intensityrecords.app.program.domain.title
import com.intensityrecords.app.workouts.presentation.workouts_details_screen.component.StatBadge
import com.intensityrecords.app.workouts.presentation.workouts_screen.component.pulseAnimation
import kotlinx.coroutines.delay

@Composable
fun ProgramCard(
    item: ProgramCollection,
    modifier: Modifier = Modifier,
    isWideScreen: Boolean,
    onClick: () -> Unit,
    dimens: AppDimens
) {
    val locale = LocalAppLocale.current

    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val isHovered by interactionSource.collectIsHoveredAsState()
    val isActive = isFocused || isHovered

    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    var cardSize by remember { mutableStateOf(IntSize.Zero) }

    LaunchedEffect(isFocused) {
        if (isFocused) {
            val size = cardSize.toSize()
            val extraBottomSpace = size.height * 0.2f
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

    val borderBrush = if (isActive) {
        Brush.horizontalGradient(
            colorStops = arrayOf(
                0.0f to Color.Transparent,
                0.3f to Color.Transparent,
                0.45f to PrimaryAccent.copy(alpha = 0.5f),
                0.5f to PrimaryAccent,
                0.55f to PrimaryAccent.copy(alpha = 0.5f),
                0.7f to Color.Transparent,
                1.0f to Color.Transparent
            )
        )
    } else {
        GlowBorderBrush
    }

    val aspectRatioForCard = if (isWideScreen) 1.2f else 2.2f

    val badgeTextSize = if (isWideScreen) 14.sp else 13.sp
    val badgeIconSize = if (isWideScreen) 20.dp else 15.dp
    val badgeInternalSpacing = if (isWideScreen) 8.dp else 4.dp
    val badgeGroupSpacing = if (isWideScreen) 10.dp else 6.dp
    val surfaceHeight = if (isWideScreen) 45.dp else 30.dp

    Card(
        modifier = modifier
            .aspectRatio(aspectRatioForCard)
            .onSizeChanged { cardSize = it }
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
                Row(modifier = Modifier.fillMaxSize()) {
                    Spacer(modifier = Modifier.weight(1f))
                    Box(modifier = Modifier.weight(2.0f).fillMaxHeight()) {
                        SubcomposeAsyncImage(
                            model = item.coverImage,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
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
                        Box(
                            modifier = Modifier.fillMaxSize().background(
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
                SubcomposeAsyncImage(
                    model = item.coverImage,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
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
            }

            Box(
                modifier = Modifier.fillMaxSize().background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.3f),
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.9f)
                        )
                    )
                )
            )

            Text(
                text = item.title(locale) ?: "",
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = if (isWideScreen) 26.sp else 21.sp),
                textAlign = if (isWideScreen) TextAlign.Center else TextAlign.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(if (isWideScreen) Alignment.Center else Alignment.TopCenter)
                    .padding(
                        horizontal = if (isWideScreen) 16.dp else 15.dp,
                        vertical = if (isWideScreen) 0.dp else 55.dp
                    )
            )

            if (!isWideScreen) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth().padding(end = 15.dp, top = if (isWideScreen) 0.dp else 65.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = null,
                        tint = PrimaryAccent,
                        modifier = Modifier.size(40.dp)
                    )
                }
            }

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
                    )
                    .align(if (isWideScreen) Alignment.BottomCenter else Alignment.Center)
                    .height(surfaceHeight)
            ) {
                StatBadge(
                    icon = Icons.Default.Timer,
                    text = "15 MIN",
                    iconSize = badgeIconSize,
                    textStyle = MaterialTheme.typography.displaySmall.copy(fontSize = badgeTextSize),
                    spacing = badgeInternalSpacing
                )
                Spacer(modifier = Modifier.width(badgeGroupSpacing))
                StatBadge(
                    icon = Icons.Default.LocalFireDepartment,
                    text = "180 KCAL",
                    iconSize = badgeIconSize,
                    textStyle = MaterialTheme.typography.displaySmall.copy(fontSize = badgeTextSize),
                    spacing = badgeInternalSpacing
                )
            }
        }
    }
}
