package com.intensityrecord.sensor.presentation.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.intensityrecord.core.presentation.SensorOccupied
import com.intensityrecord.core.presentation.SensorStandby
import com.intensityrecord.core.presentation.SensorTriggeredRed
import com.intensityrecord.core.presentation.SensorUndetected
import com.intensityrecord.sensor.domain.SensorData

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SensorCard(
    sensor: SensorData,
    buttonStatusColors: Map<String, String> = emptyMap(),
    onClick: () -> Unit = {},
    onLongClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var showTime by remember { mutableStateOf(false) }

    val backgroundColor = resolveStatusColor(sensor.status, buttonStatusColors)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = {
                    showTime = !showTime
                    onClick()
                },
                onLongClick = onLongClick
            ),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Alert indicator dot
                if (sensor.alertTriggered) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(SensorTriggeredRed)
                            .align(Alignment.End)
                    )
                }

                Text(
                    text = sensor.displayName,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                if (showTime) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = sensor.formatElapsedTime(),
                        fontSize = 12.sp,
                        color = Color.White.copy(alpha = 0.9f),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

private fun resolveStatusColor(
    status: String,
    buttonStatusColors: Map<String, String>
): Color {
    // Try to use server-provided color first
    val serverColor = buttonStatusColors[status]
    if (serverColor != null) {
        return parseHexColor(serverColor)
    }
    // Fallback to defaults
    return when (status) {
        "occupied" -> SensorOccupied
        "standby" -> SensorStandby
        "undetected" -> SensorUndetected
        else -> SensorStandby
    }
}

private fun parseHexColor(hex: String): Color {
    val cleanHex = hex.removePrefix("#")
    return try {
        Color(("FF$cleanHex").toLong(16))
    } catch (_: Exception) {
        SensorStandby
    }
}
