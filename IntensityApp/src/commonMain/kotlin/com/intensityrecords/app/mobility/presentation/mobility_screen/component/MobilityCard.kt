package com.intensityrecords.app.mobility.presentation.mobility_screen.component

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.intensityrecord.core.presentation.CardBackground
import com.intensityrecord.core.presentation.GlowBorderBrush
import com.intensityrecord.core.presentation.PrimaryAccent
import com.intensityrecord.resources.Res
import com.intensityrecord.resources.montserrat_bold
import com.intensityrecord.resources.montserrat_regular
import com.intensityrecords.app.core.domain.AppDimens
import com.intensityrecords.app.mobility.domain.MobilityItem
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource


@Composable
fun MobilityCard(item: MobilityItem, modifier: Modifier = Modifier, isWideScreen: Boolean,dimens: AppDimens) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val isHovered by interactionSource.collectIsHoveredAsState()
    val isActive = isFocused || isHovered

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
        GlowBorderBrush
    }

    val aspectRatioForCard = if (isWideScreen) 1.5f else 0.7f
    val titleFontSize = if (isWideScreen) 22.sp else 16.sp

    val borderWidth = if (isActive) dimens.borderWidthActive else dimens.borderWidthNormal
    val elevationState by animateDpAsState(if (isActive) dimens.elevationStateActive else dimens.elevationStateNormal)

//    val aspectRatioForCard = if (isWideScreen) {
//        1.5f
//    } else {
//        16f / 9f
//    }
    val textSize = if (isWideScreen) 14.sp else 10.sp
    val surfaceHeight = if (isWideScreen) 45.dp else 30.dp

    Card(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(aspectRatioForCard)
            .shadow(
                elevation = elevationState,
                shape = RoundedCornerShape(16.dp),
                spotColor = PrimaryAccent.copy(alpha = 0.6f),
                ambientColor = PrimaryAccent.copy(alpha = 0.6f)
            )
            .clip(RoundedCornerShape(16.dp))
            .border(BorderStroke(borderWidth, borderBrush), RoundedCornerShape(16.dp))
//            .focusable(interactionSource = interactionSource)
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
                color = Color.White,
                fontSize = titleFontSize,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.align(Alignment.Center).padding(8.dp), // Added padding
                fontFamily = FontFamily(Font(Res.font.montserrat_bold)),
                letterSpacing = if (isWideScreen) 1.5.sp else 0.5.sp,
                textAlign = TextAlign.Center // Ensure centered if text wraps
            )

            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
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
                            text = "10 MIN",
                            color = PrimaryAccent,
                            fontSize = textSize,
                            fontFamily = FontFamily(Font(Res.font.montserrat_bold)),
                            fontWeight = FontWeight.Bold,
                        )
                        Text(
                            text = " | LIGHT | ",
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
            }
        }
    }
}