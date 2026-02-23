package com.intensityrecords.app.home.presentation.home_screen.component

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForwardIos
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.intensityrecord.core.presentation.GlowBorderBrush
import com.intensityrecord.core.presentation.PrimaryAccent
import intensityrecordapp.intensityapp.generated.resources.Res
import intensityrecordapp.intensityapp.generated.resources.montserrat_bold
import intensityrecordapp.intensityapp.generated.resources.intro_video
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.stringResource

@Composable
fun IntroVideoButton() {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isFocused) 1.15f else 1f,
        animationSpec = tween(300),
        label = "scale"
    )

    val shadowElevation by animateDpAsState(
        targetValue = if (isFocused) 16.dp else 0.dp,
        animationSpec = tween(300),
        label = "elevation"
    )

    val borderBrush = if (isFocused) SolidColor(PrimaryAccent) else GlowBorderBrush
    val borderWidth = if (isFocused) 3.dp else 1.dp

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

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier
                .widthIn(min = 250.dp, max = 300.dp) // Constrain width (Pill shape)
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                }
                .shadow(
                    elevation = shadowElevation,
                    shape = CircleShape,
                    spotColor = PrimaryAccent,
                    ambientColor = PrimaryAccent
                )
                .onSizeChanged { cardSize = it }
                // 5. Attach the requester (MUST be before clickable/focusable)
                .bringIntoViewRequester(bringIntoViewRequester)
                .border(BorderStroke(borderWidth, borderBrush), CircleShape)
                .clip(CircleShape)
                .background(Color(0xFF111111))
                .height(64.dp)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) { /* Play Action */ }
                .padding(start = 8.dp, end = 24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon Circle
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(if (isFocused) PrimaryAccent else PrimaryAccent.copy(alpha = 0.8f),
                        CircleShape)
                    .border(1.dp, PrimaryAccent, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Filled.PlayArrow, contentDescription = null, tint = Color.Black)
            }

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = stringResource(Res.string.intro_video),
                style = TextStyle(
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    color = Color.White,
                    fontFamily = FontFamily(Font(Res.font.montserrat_bold))
                )
            )

            Spacer(modifier = Modifier.weight(1f))

            Icon(
                imageVector = Icons.AutoMirrored.Rounded.ArrowForwardIos,
                contentDescription = null,
                tint = Color.Gray,
                modifier = Modifier.size(14.dp)
            )
        }
    }
}
