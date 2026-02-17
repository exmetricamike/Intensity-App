package com.intensityrecords.app.mobility.presentation.mobility_screen.component

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FiberManualRecord
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.rounded.FiberManualRecord
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.intensityrecord.resources.Res
import com.intensityrecord.resources.montserrat_bold
import com.intensityrecord.resources.montserrat_regular
import com.intensityrecords.app.core.domain.AppDimens
import com.intensityrecords.app.core.presentation.captions
import com.intensityrecords.app.core.presentation.cardTitle
import com.intensityrecords.app.core.presentation.chipButtonText
import com.intensityrecords.app.mobility.domain.MobilityItem
import com.intensityrecords.app.workouts.presentation.workouts_details_screen.component.StatBadge
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
        targetValue = if (isFocused) 10.dp else 0.dp,
        animationSpec = tween(300),
        label = "elevation"
    )

    val borderWidth = if (isActive) dimens.borderWidthActive else dimens.borderWidthNormal

    val badgeTextSize = if (isWideScreen) 14.sp else 10.sp
    val badgeIconSize = if (isWideScreen) 20.dp else 14.dp
    val badgeInternalSpacing = if (isWideScreen) 8.dp else 4.dp
    val badgeGroupSpacing = if (isWideScreen) 10.dp else 6.dp
    val surfaceHeight = if (isWideScreen) 45.dp else 30.dp
    val surfacePaddingH = if (isWideScreen) 10.dp else 6.dp
    val badgeTextStyle = captions.copy(fontSize = badgeTextSize)


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

    val aspectRatioForCard = if (isWideScreen) 1.5f else 0.7f
    val titleFontSize = if (isWideScreen) 26.sp else 16.sp

    val elevationState by animateDpAsState(if (isActive) dimens.elevationStateActive else dimens.elevationStateNormal)
    val textSize = if (isWideScreen) 14.sp else 10.sp

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
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(item.image),
                contentDescription = item.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize().alpha(0.6f)
            )

            Text(
                text = item.title,
                style = cardTitle.copy(
                    fontSize = titleFontSize,
                    letterSpacing = if (isWideScreen) 1.5.sp else 0.5.sp,
                    textAlign = TextAlign.Center,
                ),
                modifier = Modifier.align(Alignment.Center).padding(8.dp), // Added padding
                fontFamily = FontFamily(Font(Res.font.montserrat_bold)),
            )

            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
//                Surface(
//                    color = PrimaryAccent.copy(alpha = 0.2f),
//                    shape = RoundedCornerShape(50),
//                    border = BorderStroke(1.dp, PrimaryAccent),
//                    modifier = Modifier.height(surfaceHeight)
//                ) {
//                    Row(
//                        verticalAlignment = Alignment.CenterVertically,
//                        horizontalArrangement = Arrangement.Center,
//                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 2.dp)
//                    ) {
//                        Text(
//                            text = "10 MIN",
//                            fontFamily = FontFamily(Font(Res.font.montserrat_bold)),
//                            style = chipButtonText.copy(fontSize = textSize)
//                        )
//                        Text(
//                            text = " | LIGHT | ",
//                            fontFamily = FontFamily(Font(Res.font.montserrat_regular)),
//                            style = chipButtonText.copy(fontSize = textSize, color = Color.White)
//                        )
//                        Text(
//                            text = "180 KCAL",
//                            fontFamily = FontFamily(Font(Res.font.montserrat_regular)),
//                            style = chipButtonText.copy(fontSize = textSize, color = Color.White)
//                        )
//                    }
//                }

                Surface(
                    color = PrimaryAccent.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(50),
                    border = BorderStroke(1.dp, PrimaryAccent),
                    modifier = Modifier
                        .padding(bottom = 12.dp, start = 8.dp, end = 8.dp)
                        .height(surfaceHeight)
                        .wrapContentWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(horizontal = surfacePaddingH)
                    ) {

                        StatBadge(
                            icon = Icons.Default.Timer,
                            text = "10 MIN",
                            iconSize = badgeIconSize,
                            textStyle = badgeTextStyle,
                            spacing = badgeInternalSpacing
                        )

                        Spacer(modifier = Modifier.width(badgeGroupSpacing))

                        Box(
                            modifier = Modifier
                                .width(1.dp)
                                .height(12.dp)
                                .background(PrimaryAccent.copy(0.5f))
                        )

                        Spacer(modifier = Modifier.width(badgeGroupSpacing))

                        StatBadge(
                            icon = Icons.Default.FiberManualRecord,
                            text = "LIVE",
                            iconSize = badgeIconSize,
                            textStyle = badgeTextStyle,
                            spacing = badgeInternalSpacing
                        )

                        Spacer(modifier = Modifier.width(badgeGroupSpacing))

                        Box(
                            modifier = Modifier
                                .width(1.dp)
                                .height(12.dp)
                                .background(PrimaryAccent.copy(0.5f))
                        )

                        Spacer(modifier = Modifier.width(badgeGroupSpacing))

                        StatBadge(
                            icon = Icons.Default.LocalFireDepartment,
                            text = "180 KCAL",
                            iconSize = badgeIconSize,
                            textStyle = badgeTextStyle,
                            spacing = badgeInternalSpacing
                        )

                    }
                }

            }
        }
    }
}