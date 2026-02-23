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
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CenterFocusStrong
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.intensityrecord.core.presentation.CardBackground
import com.intensityrecord.core.presentation.GlowBorderBrush
import com.intensityrecord.core.presentation.OffWhite
import com.intensityrecord.core.presentation.PrimaryAccent
import com.intensityrecords.app.core.presentation.Title
import com.intensityrecords.app.core.presentation.buttonText
import com.intensityrecords.app.core.presentation.captions
import com.intensityrecords.app.workouts.domain.WorkoutItem
import intensityrecordapp.intensityapp.generated.resources.Res
import intensityrecordapp.intensityapp.generated.resources.montserrat_bold
import intensityrecordapp.intensityapp.generated.resources.montserrat_regular
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource


@Composable
fun HeroSection(item: WorkoutItem, isWideScreen: Boolean) {

    val height = if (isWideScreen) 330.dp else 450.dp

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
        targetValue = if (isFocused) 8.dp else 0.dp,
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

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .padding(horizontal = if (isWideScreen) 10.dp else 0.dp, vertical = if (isWideScreen) 20.dp else 0.dp)
            .shadow(
                elevation = shadowElevation,
                shape = RoundedCornerShape(24.dp),
                spotColor = PrimaryAccent,
                ambientColor = PrimaryAccent
            )
            .clip(RoundedCornerShape(24.dp))
            .border(BorderStroke(borderWidth, borderBrush), RoundedCornerShape(24.dp))
//            .focusable(interactionSource = interactionSource)
            .clickable(interactionSource = interactionSource, indication = null) { },
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(height)
                .clip(RoundedCornerShape(24.dp))
                .background(Color.Black.copy(alpha = 0.6f))
        )
        {
            Row(modifier = Modifier.fillMaxSize())
            {
                Spacer(modifier = Modifier.weight(1f))
                Box(modifier = Modifier.weight(1.2f).fillMaxHeight()) {
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

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(25.dp),
                verticalArrangement = Arrangement.Center
            ) {

                val customFontSize = if (isWideScreen) 48.sp else 35.sp

                Text(
                    text = item.title.uppercase(),
                    fontFamily = FontFamily(Font(Res.font.montserrat_bold)),
                    style = Title.copy(
                        fontSize = customFontSize,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 1.sp,
                        color = OffWhite
                    )
                )

//                Spacer(modifier = Modifier.height(12.dp))
//
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
//                            text = "12 sessions this month · ${item.duration} · ${item.level} / High",
//                            fontFamily = FontFamily(Font(Res.font.montserrat_bold)),
//                            style = chipButtonText.copy(fontSize = textSize)
//                        )
//                    }
//                }

                Spacer(modifier = Modifier.height(30.dp))

                if (isWideScreen) {
                    // CTA Button
                    Button(
                        onClick = { /* Handle coach selection */ },
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryAccent),
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp)
                    ) {
                        Card(
                            modifier = Modifier
                                .height(25.dp)
                                .width(40.dp)
                                .clip(RectangleShape),
                            colors = CardDefaults.cardColors(containerColor = CardBackground),
                            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.PlayArrow,
                                    contentDescription = null,
                                    tint = PrimaryAccent,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "LET THE COACH CHOOSE",
                            fontFamily = FontFamily(Font(Res.font.montserrat_bold)),
                            style = buttonText
                        )
                    }
                } else {
                    Button(
                        onClick = { /* Handle coach selection */ },
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryAccent),
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp)
                    ) {
                        Card(
                            modifier = Modifier
                                .height(25.dp)
                                .width(30.dp)
                                .clip(RectangleShape),
                            colors = CardDefaults.cardColors(containerColor = CardBackground),
                            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.PlayArrow,
                                    contentDescription = null,
                                    tint = PrimaryAccent,
                                    modifier = Modifier.size(14.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "LET THE COACH CHOOSE",
                            fontFamily = FontFamily(Font(Res.font.montserrat_bold)),
                            style = buttonText
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Surprise workout selected by your coach",
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = if (isWideScreen) 16.dp else 0.dp),
                    style = captions,
                    fontFamily = FontFamily(Font(Res.font.montserrat_regular))
                )

                Spacer(modifier = Modifier.height(30.dp))

                if (isWideScreen) {
                    // Stats Row
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        StatBadge(icon = Icons.Default.Timer, text = "Average ${item.duration}")
                        Spacer(modifier = Modifier.width(24.dp))
                        StatBadge(
                            icon = Icons.Default.LocalFireDepartment,
                            text = "High calorie burn"
                        )
                        Spacer(modifier = Modifier.width(24.dp))
                        StatBadge(
                            icon = Icons.Default.CenterFocusStrong,
                            text = "Focus glutes + legs"
                        )
                    }
                } else {
                    Column(
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        StatBadge(icon = Icons.Default.Timer, text = "Average ${item.duration}")
                        StatBadge(
                            icon = Icons.Default.LocalFireDepartment,
                            text = "High calorie burn"
                        )
                        StatBadge(
                            icon = Icons.Default.CenterFocusStrong,
                            text = "Focus glutes + legs"
                        )
                    }
                }
            }
        }

    }
}
