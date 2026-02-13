package com.intensityrecords.app.home.presentation.home_screen.component

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
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.intensityrecord.app.Route
import com.intensityrecord.core.presentation.CardBackground
import com.intensityrecord.core.presentation.GlowBorderBrush
import com.intensityrecord.core.presentation.PrimaryAccent
import com.intensityrecord.core.presentation.TextWhite
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import com.intensityrecord.resources.Res
import com.intensityrecord.resources.montserrat_bold
import com.intensityrecord.resources.montserrat_regular
import com.intensityrecord.resources._1
import com.intensityrecords.app.core.presentation.utils.LocalAppDimens

@Composable
fun VideoOfTheDayCard(navController: NavController, isWideScreen: Boolean) {
    val dimens = LocalAppDimens.current

    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val isHovered by interactionSource.collectIsHoveredAsState()
    val isActive = isFocused || isHovered

    // 1. GLOW & STROKE LOGIC
    // We use 'colorStops' to squeeze the green into the center.
    // 0.0 to 0.3 is Transparent
    // 0.5 is Green (The peak)
    // 0.7 to 1.0 is Transparent again
    // This makes the stroke appear "shorter" horizontally.
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
        GlowBorderBrush // Default subtle gradient
    }

    val borderWidth = if (isActive) dimens.borderActive else dimens.borderNormal
    // Standard elevation for depth, but NO Green spotColor (removes the "whole border" glow)
    val elevationState by animateDpAsState(if (isActive) 9.dp else 4.dp)

    val textSize = if (isWideScreen) 14.sp else 10.sp
    val surfaceHeight = if (isWideScreen) 45.dp else 30.dp
    val buttonElevation by animateDpAsState(if (isActive) 20.dp else 0.dp)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(dimens.videoCardHeight)
            .shadow(
                elevation = elevationState,
                shape = RoundedCornerShape(dimens.cornerRadius),
                spotColor = PrimaryAccent.copy(alpha = 0.6f), // <--- MAKES IT GLOW GREEN
                ambientColor = PrimaryAccent.copy(alpha = 0.6f),
            )
            .clip(RoundedCornerShape(dimens.cornerRadius))
            .background(CardBackground)
            .border(BorderStroke(borderWidth, borderBrush), RoundedCornerShape(dimens.cornerRadius))
//            .focusable(interactionSource = interactionSource)
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                navController.navigate(Route.VideoDetail)
            }
    ) {
        Box(modifier = Modifier.fillMaxSize().background(Color(0xFF222222))) {
            Image(
                painter = painterResource(resource = Res.drawable._1),
                contentDescription = "Video Thumbnail",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        // Gradient Overlay
        Box(
            modifier = Modifier.fillMaxSize().background(
                Brush.horizontalGradient(
                    colors = listOf(Color.Black.copy(alpha = 0.9f), Color.Transparent),
                    startX = 0f, endX = 1500f
                )
            )
        )

        // Text Content
        Column(
            modifier = Modifier.align(Alignment.CenterStart).padding(dimens.paddingLarge)
                .fillMaxWidth(0.7f)
        ) {
            Text(
                "VIDEO OF THE DAY",
                style = TextStyle(
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = dimens.titleLarge,
                    fontFamily = FontFamily(Font(Res.font.montserrat_bold))
                ),
                color = TextWhite
            )
            Spacer(modifier = Modifier.height(8.dp))
            Surface(
                color = PrimaryAccent.copy(alpha = 0.2f),
                shape = RoundedCornerShape(50),
                border = BorderStroke(1.dp, PrimaryAccent),
                modifier = Modifier.height(surfaceHeight)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = "8 min",
                        color = PrimaryAccent,
                        fontSize = textSize,
                        fontFamily = FontFamily(Font(Res.font.montserrat_bold)),
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = " | ABS | ",
                        color = Color.White,
                        fontSize = textSize,
                        fontFamily = FontFamily(Font(Res.font.montserrat_regular)),
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = "180 KCAL",
                        color = Color.White,
                        fontSize = textSize,
                        fontFamily = FontFamily(Font(Res.font.montserrat_regular)),

                        fontWeight = FontWeight.Bold,
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "Today's Workout: Glutes & Core",
                style = TextStyle(
                    fontWeight = FontWeight.Medium,
                    fontSize = dimens.bodyMedium,
                    color = Color.White,
                    fontFamily = FontFamily(Font(Res.font.montserrat_regular))
                )
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = {
                    navController.navigate(Route.VideoDetail)
                },
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryAccent),
                shape = RoundedCornerShape(24),
                modifier = Modifier.height(dimens.buttonHeight)
                    .shadow(
                        elevation = buttonElevation,
                        shape = RoundedCornerShape(24),
                        spotColor = PrimaryAccent,
                        ambientColor = PrimaryAccent
                    )
            ) {
                Text(
                    "START NOW",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = dimens.buttonText,
                    fontFamily = FontFamily(Font(Res.font.montserrat_bold))
                )
            }
        }
    }
}
