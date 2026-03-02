package com.intensityrecords.app.mobility.presentation.mobility_screen.component

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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Timer
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
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.intensityrecord.core.presentation.CardBackground
import com.intensityrecord.core.presentation.GlowBorderBrush
import com.intensityrecord.core.presentation.PrimaryAccent
import com.intensityrecords.app.core.domain.AppDimens
import com.intensityrecords.app.mobility.domain.MobilityItem
import com.intensityrecords.app.workouts.presentation.workouts_details_screen.component.StatBadge
import intensityrecordapp.intensityapp.generated.resources.Res
import intensityrecordapp.intensityapp.generated.resources.montserrat_bold
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource


@Composable
fun MobilityCard(
    item: MobilityItem,
    modifier: Modifier = Modifier,
    isWideScreen: Boolean,
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

    val borderWidth = if (isActive) dimens.borderWidthActive else dimens.borderWidthNormal

    val badgeTextSize = if (isWideScreen) 14.sp else 13.sp
    val badgeIconSize = if (isWideScreen) 20.dp else 15.dp
    val badgeInternalSpacing = if (isWideScreen) 8.dp else 4.dp
    val surfaceHeight = if (isWideScreen) 45.dp else 30.dp

    val borderBrush = if (isActive) {
        Brush.horizontalGradient(
            colorStops = arrayOf(
                0.0f to PrimaryAccent.copy(alpha = 0.3f),
                0.3f to PrimaryAccent.copy(alpha = 0.5f),
                0.45f to PrimaryAccent.copy(alpha = 0.8f),
                0.5f to PrimaryAccent,
                0.55f to PrimaryAccent.copy(alpha = 0.8f),
                0.7f to PrimaryAccent.copy(alpha = 0.5f),
                1.0f to PrimaryAccent.copy(alpha = 0.3f)
            )
        )
    } else {
        GlowBorderBrush
    }

    val aspectRatioForCard = if (isWideScreen) 1.5f else 2.2f
    val titleFontSize = if (isWideScreen) 26.sp else 21.sp

    Card(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(aspectRatioForCard)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .onSizeChanged { cardSize = it }
            // 5. Attach the requester (MUST be before clickable/focusable)
            .bringIntoViewRequester(bringIntoViewRequester)
            .shadow(
                elevation = shadowElevation,
                shape = RoundedCornerShape(16.dp),
                spotColor = PrimaryAccent,
                ambientColor = PrimaryAccent
            )
            .clip(RoundedCornerShape(16.dp))
            .border(BorderStroke(borderWidth, borderBrush), RoundedCornerShape(16.dp))
            .clickable(interactionSource = interactionSource, indication = null) { },
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
                        Image(
                            painter = painterResource(item.image),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
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
                Image(
                    painter = painterResource(item.image),
                    contentDescription = item.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize().alpha(0.7f)
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
                text = item.title,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = titleFontSize,
                    textAlign = if (isWideScreen) TextAlign.Center else TextAlign.Start
                ),

                modifier = Modifier
                    .fillMaxWidth()
                    .align(if (isWideScreen) Alignment.Center else Alignment.TopCenter) // Places text in the dead center
                    .padding(
                        horizontal = if (isWideScreen) 8.dp else 15.dp,
                        vertical = if (isWideScreen) 8.dp else 55.dp
                    )
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
                    text = "10 MIN",
                    iconSize = badgeIconSize,
                    textStyle = MaterialTheme.typography.displaySmall.copy(fontSize = badgeTextSize),
                    spacing = badgeInternalSpacing
                )
            }

        }
    }
}