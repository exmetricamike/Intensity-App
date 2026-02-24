package com.intensityrecords.app.live.presentation.live_screen.component


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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccessTime
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.intensityrecord.core.presentation.CardBackground
import com.intensityrecord.core.presentation.GlowBorderBrush
import com.intensityrecord.core.presentation.PrimaryAccent
import com.intensityrecords.app.core.presentation.chipButtonText
import com.intensityrecords.app.home.presentation.home_screen.component.VideoPlayerAutoPlayPlaceholder
import intensityrecordapp.intensityapp.generated.resources.Res
import intensityrecordapp.intensityapp.generated.resources._4
import intensityrecordapp.intensityapp.generated.resources.montserrat_bold
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource

@Composable
fun MainVideoPlayerCard(isWideScreen: Boolean) {
    val cardHeight = if (isWideScreen) 300.dp else 250.dp
    val cardWidth = if (isWideScreen) 600.dp else 350.dp

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
    val elevation by animateDpAsState(if (isFocused) 24.dp else 8.dp)
    val elevationState by animateDpAsState(if (isActive) 9.dp else 4.dp)

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

    val textSize = if (isWideScreen) 14.sp else 10.sp
    val surfaceHeight = if (isWideScreen) 45.dp else 30.dp

    var timeLeftInSeconds by remember { mutableStateOf(600) } // 10 minutes = 600 seconds
    var isTimerFinished by remember { mutableStateOf(false) }

    // This effect runs every time the screen is opened (enters composition)
    LaunchedEffect(Unit) {
        while (timeLeftInSeconds > 0) {
            delay(1000L) // Wait 1 second
            timeLeftInSeconds--
        }
        isTimerFinished = true
    }

    // Format time to MM:SS safely for Kotlin Multiplatform
    val minutes = (timeLeftInSeconds / 60).toString().padStart(2, '0')
    val seconds = (timeLeftInSeconds % 60).toString().padStart(2, '0')
    val timeString = "$minutes:$seconds"

    Box(
        modifier = Modifier
            .width(cardWidth)
            .height(cardHeight)
//            .graphicsLayer {
//                scaleX = scale
//                scaleY = scale
//            }
//            .shadow(
//                elevation = shadowElevation,
//                shape = RoundedCornerShape(24.dp),
//                spotColor = PrimaryAccent,
//                ambientColor = PrimaryAccent
//            )
            .clip(RoundedCornerShape(24.dp))
            .background(CardBackground)
            .border(BorderStroke(borderWidth, borderBrush), RoundedCornerShape(24.dp))
//            .focusable(interactionSource = interactionSource)
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) { /* Play Video */ }
    ) {

        if (isTimerFinished) {
            // Show YouTube Video
            VideoPlayerAutoPlayPlaceholder(
                modifier = Modifier.fillMaxSize()
            )
        } else {

            Image(
                painter = painterResource(Res.drawable._4),
                contentDescription = "Live Class Preview",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize().alpha(0.8f)
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.4f))
            )

            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 24.dp),
                verticalAlignment = Alignment.CenterVertically
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
                        Icon(
                            imageVector = Icons.Rounded.AccessTime,
                            contentDescription = null,
                            tint = PrimaryAccent,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = timeString, // Updated text
                            fontFamily = FontFamily(Font(Res.font.montserrat_bold)),
                            style = chipButtonText.copy(fontSize = textSize)
                        )
                    }
                }
            }
        }
    }
}