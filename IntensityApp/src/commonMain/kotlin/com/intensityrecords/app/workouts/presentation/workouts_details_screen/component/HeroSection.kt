package com.intensityrecords.app.workouts.presentation.workouts_details_screen.component

import androidx.compose.animation.core.animateDpAsState
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
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CenterFocusStrong
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.intensityrecord.core.presentation.CardBackground
import com.intensityrecord.core.presentation.GlowBorderBrush
import com.intensityrecord.core.presentation.PrimaryAccent
import com.intensityrecords.app.workouts.domain.WorkoutItem
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import com.intensityrecord.resources.Res
import com.intensityrecord.resources.montserrat_bold
import com.intensityrecord.resources.montserrat_regular


@Composable
fun HeroSection(item: WorkoutItem, isWideScreen: Boolean) {

    val height = if (isWideScreen) 380.dp else 450.dp


    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val isHovered by interactionSource.collectIsHoveredAsState()
    val isActive = isFocused || isHovered

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

    val borderWidth = if (isFocused) 3.dp else 1.dp
    val elevationState by animateDpAsState(if (isActive) 6.5.dp else 2.dp)


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .shadow(
                elevation = elevationState,
                shape = RoundedCornerShape(24.dp),
                spotColor = PrimaryAccent.copy(alpha = 0.4f),
                ambientColor = PrimaryAccent.copy(alpha = 0.4f)
            )
            .clip(RoundedCornerShape(24.dp))
            .border(BorderStroke(borderWidth, borderBrush), RoundedCornerShape(24.dp))
            .focusable(interactionSource = interactionSource)
            .clickable(interactionSource = interactionSource, indication = null) { },
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(height) // Adjust height to match screenshot
//                .border(
//                    border = BorderStroke(
//                        2.dp, Brush.horizontalGradient(
//                            colors = listOf(PrimaryAccent, PrimaryAccent.copy(alpha = 0.5f))
//                        )
//                    ),
//                    shape = RoundedCornerShape(24.dp)
//                )
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
                    color = Color(0xFFF0F0F0), // Off-white
                    fontSize = customFontSize,
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = FontFamily(Font(Res.font.montserrat_bold)),
                    letterSpacing = 1.sp,
                )

                Spacer(modifier = Modifier.height(12.dp))


                Text(
                    text = "12 sessions this month · ${item.duration} · ${item.level} / High",
                    color = Color.LightGray,
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(Res.font.montserrat_regular))
                )

                Spacer(modifier = Modifier.height(30.dp))

                if (isWideScreen) {
                    // CTA Button
                    Button(
                        onClick = { /* Handle coach selection */ },
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryAccent),
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp)
                    ) {
                        Text(
                            text = "LET THE COACH CHOOSE",
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(Res.font.montserrat_bold))
                        )
                    }
                } else {
                    Button(
                        onClick = { /* Handle coach selection */ },
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryAccent),
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp)
                    ) {
                        Text(
                            text = "LET THE COACH CHOOSE",
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(Res.font.montserrat_bold))
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Surprise workout selected by your coach",
                    color = Color.Gray,
                    fontSize = 14.sp,
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
