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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.intensityrecord.core.presentation.GlowBorderBrush
import com.intensityrecord.core.presentation.PrimaryAccent
import com.intensityrecords.app.workouts.domain.Session
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import com.intensityrecord.resources.Res
import com.intensityrecord.resources.montserrat_bold
import com.intensityrecord.resources.montserrat_regular
import com.intensityrecords.app.core.domain.AppDimens


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
    val isSelected = isFocused || isHovered
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
    val cardWidth = if (isWideScreen) 280.dp else 170.dp
    val cardAspectRatio = if (isWideScreen) 1.2f else 0.85f

    Card(
        modifier = Modifier
            .width(cardWidth)
            .aspectRatio(cardAspectRatio)
            .shadow(
                elevation = elevationState,
                shape = RoundedCornerShape(20.dp), // Matches the roundness in your image
                spotColor = PrimaryAccent.copy(alpha = 0.5f),
                ambientColor = PrimaryAccent.copy(alpha = 0.5f)
            )
            .border(BorderStroke(borderWidth, borderBrush), RoundedCornerShape(20.dp))
            .clip(RoundedCornerShape(20.dp))
            .focusable(interactionSource = interactionSource)
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
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = session.title,
                        color = Color.White,
                        fontSize = dimens.sessionTitle, // Larger font like in your image
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = FontFamily(Font(Res.font.montserrat_bold)),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "${session.duration} · ${session.level}",
                        color = Color.Gray,
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(Res.font.montserrat_regular))
                    )
                }
            }

            // 3. Play Button Overlay (Positioned like the image)
            Box(
                modifier = Modifier
                    .align(Alignment.CenterEnd) // Centers vertically relative to the whole card
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