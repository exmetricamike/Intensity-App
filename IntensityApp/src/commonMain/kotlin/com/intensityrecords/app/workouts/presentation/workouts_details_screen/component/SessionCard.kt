package com.intensityrecords.app.workouts.presentation.workouts_details_screen.component

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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.intensityrecord.core.presentation.GlowBorderBrush
import com.intensityrecord.core.presentation.PrimaryAccent
import com.intensityrecords.app.core.domain.AppDimens
import com.intensityrecords.app.workouts.domain.Session
import intensityrecordapp.intensityapp.generated.resources.Res
import intensityrecordapp.intensityapp.generated.resources.montserrat_bold
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource


@Composable
fun SessionCard(
    session: Session,
    isWideScreen: Boolean,
    onClick: () -> Unit,
    dimens: AppDimens
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val isHovered by interactionSource.collectIsHoveredAsState()
    val isActive = isFocused || isHovered

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

    val borderWidth = if (isFocused) 3.dp else 1.dp

    val borderBrush = if (isActive) {
        Brush.horizontalGradient(
            colorStops = arrayOf(
                0.0f to Color.Transparent,
                0.35f to Color.Transparent,
                0.5f to PrimaryAccent,
                0.65f to Color.Transparent,
                1.0f to Color.Transparent
            )
        )
    } else {
        GlowBorderBrush
    }

    val elevationState by animateDpAsState(if (isActive) 8.5.dp else 2.dp)
    val cardWidth = if (isWideScreen) 280.dp else 170.dp
    val cardAspectRatio = if (isWideScreen) 1.2f else 0.85f
    val textSize = if (isWideScreen) 14.sp else 10.sp

    val badgeTextSize = if (isWideScreen) 14.sp else 8.sp
    val badgeIconSize = if (isWideScreen) 20.dp else 10.dp
    val badgeInternalSpacing = if (isWideScreen) 8.dp else 2.dp
    val badgeGroupSpacing = if (isWideScreen) 10.dp else 4.dp
    val surfaceHeight = if (isWideScreen) 45.dp else 35.dp

    Card(
        modifier = Modifier
            .width(cardWidth)
            .aspectRatio(cardAspectRatio)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .shadow(
                elevation = shadowElevation,
                shape = RoundedCornerShape(20.dp), // Matches the roundness in your image
                spotColor = PrimaryAccent,
                ambientColor = PrimaryAccent
            )
            .border(BorderStroke(borderWidth, borderBrush), RoundedCornerShape(20.dp))
            .clip(RoundedCornerShape(20.dp))
            .clickable(interactionSource = interactionSource, indication = null, onClick = onClick),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Black) // Solid dark background
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {
                // 1. Top Image Section
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1.3f) // Image takes more space
                        .padding(8.dp) // Creates that inner margin look
                        .clip(RoundedCornerShape(16.dp))
                ) {
                    Image(
                        painter = painterResource(session.image),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                // 2. Bottom Text Section
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.7f) // Text area
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = session.title,
                        fontFamily = FontFamily(Font(Res.font.montserrat_bold)),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = TextStyle(
                            fontWeight = FontWeight.SemiBold,
                            fontSize = dimens.sessionTitle,
                            letterSpacing = 1.5.sp,
                            color = Color.White
                        )
                    )

                    Spacer(modifier = Modifier.height(4.dp))

//                    Surface(
//                        color = PrimaryAccent.copy(alpha = 0.2f),
//                        shape = RoundedCornerShape(50),
//                        border = BorderStroke(1.dp, PrimaryAccent),
//                        modifier = Modifier.height(surfaceHeight).fillMaxWidth()
//                    ) {
//                        Row(
//                            verticalAlignment = Alignment.CenterVertically,
//                            horizontalArrangement = Arrangement.Center,
//                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 2.dp)
//                        ) {
//                            Text(
//                                text = session.duration.uppercase(),
//                                fontFamily = FontFamily(Font(Res.font.montserrat_bold)),
//                                style = chipButtonText.copy(fontSize = textSize)
//                            )
//                            Text(
//                                text = " | 180 KCAL",
//                                style = chipButtonText.copy(
//                                    fontSize = textSize,
//                                    color = Color.White
//                                ),
//                                fontFamily = FontFamily(Font(Res.font.montserrat_regular)),
//                            )
//                        }
//                    }

//                    Surface(
//                        color = PrimaryAccent.copy(alpha = 0.2f),
//                        shape = RoundedCornerShape(50),
//                        border = BorderStroke(1.dp, PrimaryAccent),
//                        modifier = Modifier
//                            .padding(bottom = 8.dp, start = 2.dp, end = 2.dp)
//                            .height(surfaceHeight)
//                            .wrapContentWidth()
//                    ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .padding(bottom = 8.dp, start = 2.dp, end = 2.dp)
                            .height(surfaceHeight)
                            .wrapContentWidth()
                    ) {

                        StatBadge(
                            icon = Icons.Default.Timer,
                            text = session.duration.uppercase(),
                            iconSize = badgeIconSize,
                            textStyle = MaterialTheme.typography.displaySmall.copy(fontSize = badgeTextSize),
                            spacing = badgeInternalSpacing
                        )

//                        Spacer(modifier = Modifier.width(badgeGroupSpacing))
//
//                        Box(
//                            modifier = Modifier
//                                .width(1.dp)
//                                .height(12.dp)
//                                .background(PrimaryAccent.copy(0.5f))
//                        )

                        Spacer(modifier = Modifier.width(badgeGroupSpacing))

                        StatBadge(
                            icon = Icons.Default.LocalFireDepartment,
                            text = "180 KCAL",
                            iconSize = badgeIconSize,
                            textStyle = MaterialTheme.typography.displaySmall.copy(fontSize = badgeTextSize),
                            spacing = badgeInternalSpacing
                        )

                    }
//                    }
                }
            }

            // 3. Play Button Overlay (Positioned like the image)
            Box(
                modifier = Modifier
                    .align(Alignment.Center) // Centers vertically relative to the whole card
                    .padding(end = 12.dp)
                    .size(42.dp)
                    .background(Color.Black, shape = RoundedCornerShape(50)) // Outer black ring
                    .border(1.dp, PrimaryAccent.copy(alpha = 0.5f), RoundedCornerShape(50))
                    .padding(4.dp) // Spacing for the inner green circle
                    .background(PrimaryAccent.copy(alpha = 0.2f), shape = RoundedCornerShape(50)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Play",
                    tint = PrimaryAccent, // Lime/Green icon
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}