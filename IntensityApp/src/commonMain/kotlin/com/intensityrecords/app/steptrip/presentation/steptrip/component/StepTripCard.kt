package com.intensityrecords.app.steptrip.presentation.steptrip.component

import androidx.compose.animation.core.animateDpAsState
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.SubcomposeAsyncImage
import com.intensityrecord.core.presentation.CardBackground
import com.intensityrecord.core.presentation.PrimaryAccent
import com.intensityrecords.app.core.presentation.utils.LocalAppLocale
import com.intensityrecords.app.steptrip.domain.StepTripItem
import com.intensityrecords.app.steptrip.domain.category
import com.intensityrecords.app.steptrip.domain.title
import com.intensityrecords.app.workouts.presentation.workouts_screen.component.pulseAnimation

@Composable
fun StepTripCard(
    item: StepTripItem,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val locale = LocalAppLocale.current
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val isHovered by interactionSource.collectIsHoveredAsState()
    val isActive = isFocused || isHovered

    val shadowElevation by animateDpAsState(
        targetValue = if (isActive) 12.dp else 6.dp,
        animationSpec = tween(300), label = "elevation"
    )

    val borderBrush = if (isActive) {
        Brush.linearGradient(colors = listOf(PrimaryAccent, PrimaryAccent))
    } else {
        Brush.linearGradient(
            colors = listOf(PrimaryAccent.copy(alpha = 0.5f), PrimaryAccent.copy(alpha = 0.1f))
        )
    }

    Card(
        modifier = modifier
            .fillMaxSize()
            .shadow(shadowElevation, RoundedCornerShape(20.dp), spotColor = PrimaryAccent)
            .clip(RoundedCornerShape(20.dp))
            .border(BorderStroke(1.dp, borderBrush), RoundedCornerShape(20.dp))
            .clickable(interactionSource = interactionSource, indication = null) { },
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .weight(0.2f)
                    .background(Color.Black)
            ) {
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

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 15.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = item.title(locale),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        lineHeight = 22.sp,
                    ),
                )

                Spacer(modifier = Modifier.height(10.dp))

                item.category(locale)?.let { cat ->
                    Box(
                        modifier = Modifier
                            .border(1.dp, Color.DarkGray, RoundedCornerShape(8.dp))
                            .padding(horizontal = 12.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = cat.uppercase(),
                            style = MaterialTheme.typography.displaySmall.copy(
                                color = PrimaryAccent,
                                fontSize = 11.sp
                            ),
                        )
                    }
                    Spacer(modifier = Modifier.height(15.dp))
                }

                Button(
                    onClick = onClick,
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryAccent),
                    shape = RoundedCornerShape(50),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(36.dp)
                ) {
                    Text(
                        text = "INFO",
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = Color.Black,
                            fontSize = 11.sp,
                        ),
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    item.durationMin?.let { StatItem(Icons.Default.DirectionsRun, "$it min") }
                    item.distanceKm?.let { StatItem(Icons.Default.Schedule, "${it.trimEnd('0').trimEnd('.')} km") }
                    item.caloriesBurned?.let { StatItem(Icons.Default.LocalFireDepartment, "$it kcal") }
                }
            }
        }
    }
}
