package com.intensityrecords.app.home.presentation.home_screen.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.intensityrecord.app.Route
import com.intensityrecord.core.presentation.CardBackground
import com.intensityrecord.core.presentation.GlowBorderBrush
import com.intensityrecord.core.presentation.PrimaryAccent
import com.intensityrecord.core.presentation.TextWhite
import com.intensityrecord.resources.Res
import com.intensityrecord.resources.montserrat_bold
import com.intensityrecords.app.core.domain.AppDimens
import com.intensityrecords.app.core.presentation.buttonText
import com.intensityrecords.app.home.domain.HomeItem
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource

@Composable
fun ContentCard(item: HomeItem, width: Dp, aspectRatio: Float, navController: NavController,dimens: AppDimens) {
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

    val borderWidth = if (isActive) dimens.borderWidthActive else dimens.borderWidthNormal
    val elevationState = if (isActive) 12.dp else 4.dp

    Card(
        modifier = Modifier
            .width(width)
            .aspectRatio(aspectRatio)
            .shadow(
                elevation = elevationState,
                shape = RoundedCornerShape(dimens.cardCornerRadius),
                spotColor = PrimaryAccent.copy(alpha = 0.4f),
                ambientColor = PrimaryAccent.copy(alpha = 0.4f)
            )
            .clip(RoundedCornerShape(dimens.cardCornerRadius))
            .border(BorderStroke(borderWidth, borderBrush), RoundedCornerShape(dimens.cardCornerRadius))
            .clickable(interactionSource = interactionSource, indication = null) {
                when (item.title) {
                    "Workout" -> {
                        navController.navigate(Route.WorkOuts)
                    }

                    "Mobility" -> {
                        navController.navigate(Route.Mobility)
                    }

                    "Live Class" -> {
                        navController.navigate(Route.Live)
                    }
                }
            },
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.fillMaxSize().background(Color(0xFF1A1A1A))) {
                Image(
                    painter = painterResource(resource = item.icon),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
            Box(
                modifier = Modifier.fillMaxSize().background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.8f)
                        )
                    )
                )
            )
            Text(
                text = item.title.uppercase(),
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = dimens.title,
                    fontFamily = FontFamily(Font(Res.font.montserrat_bold)),
                    letterSpacing = 0.1.sp
                ),
                color = Color.White,
                modifier = Modifier.align(Alignment.BottomStart).padding(16.dp)
            )
            if (item.isLive) {
                Box(
                    modifier = Modifier.align(Alignment.TopStart).padding(12.dp)
                        .background(PrimaryAccent, RoundedCornerShape(4.dp))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        "LIVE",
                        style = buttonText.copy(fontSize = dimens.live),
                        fontFamily = FontFamily(Font(Res.font.montserrat_bold))
                    )
                }
            }
        }
    }
}
