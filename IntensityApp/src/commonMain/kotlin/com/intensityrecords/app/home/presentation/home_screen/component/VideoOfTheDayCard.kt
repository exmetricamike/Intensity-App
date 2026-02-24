package com.intensityrecords.app.home.presentation.home_screen.component


//@Composable
//fun VideoOfTheDayCard(navController: NavController, isWideScreen: Boolean,dynamicHeight: Dp) {
//    val dimens = LocalAppDimens.current
//
//    val interactionSource = remember { MutableInteractionSource() }
//    val isFocused by interactionSource.collectIsFocusedAsState()
//    val isHovered by interactionSource.collectIsHoveredAsState()
//    val isActive = isFocused || isHovered
//
//    val scale by animateFloatAsState(
//        targetValue = if (isFocused) 1.10f else 1f,
//        animationSpec = tween(300),
//        label = "scale"
//    )
//
//    val shadowElevation by animateDpAsState(
//        targetValue = if (isFocused) 14.dp else 0.dp,
//        animationSpec = tween(300),
//        label = "elevation"
//    )
//
//    val borderWidth = if (isFocused) dimens.borderActive else dimens.borderNormal
//
//    // Inner Button Animation
//    val buttonElevation by animateDpAsState(if (isFocused) 22.dp else 0.dp)
//
//    // 1. GLOW & STROKE LOGIC
//    // We use 'colorStops' to squeeze the green into the center.
//    // 0.0 to 0.3 is Transparent
//    // 0.5 is Green (The peak)
//    // 0.7 to 1.0 is Transparent again
//    // This makes the stroke appear "shorter" horizontally.
//    val borderBrush = if (isActive) {
//        Brush.horizontalGradient(
//            colorStops = arrayOf(
//                0.0f to PrimaryAccent.copy(alpha = 0.4f),
//                0.2f to PrimaryAccent.copy(alpha = 0.8f),
//                0.5f to PrimaryAccent,
//                0.8f to PrimaryAccent.copy(alpha = 0.8f),
//                1.0f to PrimaryAccent.copy(alpha = 0.4f)
//            )
//        )
//    } else {
//        GlowBorderBrush // Default subtle gradient
//    }
//
//    Box(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(dynamicHeight)
//            .graphicsLayer {
//                scaleX = scale
//                scaleY = scale
//            }
//            .shadow(
//                elevation = shadowElevation,
//                shape = RoundedCornerShape(dimens.cornerRadius),
//                spotColor = PrimaryAccent, // <--- MAKES IT GLOW GREEN
//                ambientColor = PrimaryAccent,
//            )
//            .clip(RoundedCornerShape(dimens.cornerRadius))
//            .background(CardBackground)
//            .border(BorderStroke(borderWidth, borderBrush), RoundedCornerShape(dimens.cornerRadius))
////            .focusable(interactionSource = interactionSource)
//            .clickable(
//                interactionSource = interactionSource,
//                indication = null
//            ) {
//                navController.navigate(Route.VideoDetail)
//            }
//    ) {
//        Box(modifier = Modifier.fillMaxSize().background(Color(0xFF222222))) {
//            Image(
//                painter = painterResource(resource = Res.drawable._1),
//                contentDescription = "Video Thumbnail",
//                contentScale = ContentScale.Crop,
//                modifier = Modifier.fillMaxSize()
//            )
//        }
//
//        // Gradient Overlay
//        Box(
//            modifier = Modifier.fillMaxSize().background(
//                Brush.horizontalGradient(
//                    colors = listOf(Color.Black.copy(alpha = 0.9f), Color.Transparent),
//                    startX = 0f, endX = 1500f
//                )
//            )
//        )
//
//        // Text Content
//        Column(
//            modifier = Modifier.align(Alignment.CenterStart).padding(dimens.paddingLarge)
//                .fillMaxWidth(0.7f)
//        ) {
//            Text(
//                "VIDEO OF THE DAY",
//                style = cardTitle.copy(
//                    fontSize = dimens.titleLarge,
//                    fontFamily = FontFamily(Font(Res.font.montserrat_bold))
//                )
//            )
//            Spacer(modifier = Modifier.height(8.dp))
//            Text(
//                "Your daily 8-minute abs workout",
//                style = TextStyle(
//                    fontWeight = FontWeight.Medium,
//                    fontSize = dimens.bodyMedium,
//                    color = Color.White,
//                    fontFamily = FontFamily(Font(Res.font.montserrat_regular))
//                )
//            )
//            Spacer(modifier = Modifier.height(16.dp))
//            Button(
//                onClick = {
//                    navController.navigate(Route.VideoDetail)
//                },
//                colors = ButtonDefaults.buttonColors(containerColor = PrimaryAccent),
//                shape = RoundedCornerShape(24),
//                modifier = Modifier.height(dimens.buttonHeight)
//                    .shadow(
//                        elevation = buttonElevation,
//                        shape = RoundedCornerShape(24),
//                        spotColor = PrimaryAccent,
//                        ambientColor = PrimaryAccent
//                    )
//            ) {
//                Text(
//                    "START NOW",
//                    style = buttonText.copy(fontSize = dimens.buttonText),
//                    fontFamily = FontFamily(Font(Res.font.montserrat_bold))
//                )
//            }
//            Spacer(modifier = Modifier.height(10.dp))
//            if (isWideScreen) {
//                Row(verticalAlignment = Alignment.CenterVertically) {
//                    StatBadge(icon = Icons.Default.Timer, text = "Average 15-20 min")
//                    Spacer(modifier = Modifier.width(24.dp))
//                    StatBadge(icon = Icons.Default.LocalFireDepartment, text = "Bodyweight")
//                    Spacer(modifier = Modifier.width(24.dp))
//                    StatBadge(icon = Icons.Default.CenterFocusStrong, text = "Focus glutes + legs")
//                }
//            } else {
//                Column(
//                    horizontalAlignment = Alignment.Start,
//                    verticalArrangement = Arrangement.spacedBy(12.dp)
//                ) {
//                    StatBadge(icon = Icons.Default.Timer, text = "Average 15-20 min")
//                    StatBadge(icon = Icons.Default.LocalFireDepartment, text = "Bodyweight")
//                    StatBadge(icon = Icons.Default.CenterFocusStrong, text = "Focus glutes + legs")
//                }
//            }
////            Surface(
////                color = PrimaryAccent.copy(alpha = 0.2f),
////                shape = RoundedCornerShape(50),
////                border = BorderStroke(1.dp, PrimaryAccent),
////                modifier = Modifier.height(surfaceHeight)
////            ) {
////                Row(
////                    verticalAlignment = Alignment.CenterVertically,
////                    horizontalArrangement = Arrangement.Center,
////                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 2.dp)
////                ) {
////                    Text(
////                        text = "",
////                        fontFamily = FontFamily(Font(Res.font.montserrat_bold)),
////                        style = chipButtonText.copy(fontSize = textSize)
////                    )
////                    Text(
////                        text = " | ABS | ",
////                        fontFamily = FontFamily(Font(Res.font.montserrat_regular)),
////                        style = chipButtonText.copy(fontSize = textSize, color = Color.White)
////                    )
////                    Text(
////                        text = "180 KCAL",
////                        fontFamily = FontFamily(Font(Res.font.montserrat_regular)),
////                        style = chipButtonText.copy(fontSize = textSize, color = Color.White)
////                    )
////                }
////            }
//        }
//    }
//}

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CenterFocusStrong
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import com.intensityrecord.core.presentation.CardBackground
import com.intensityrecord.core.presentation.GlowBorderBrush
import com.intensityrecord.core.presentation.PrimaryAccent
import com.intensityrecords.app.core.domain.AppDimens
import com.intensityrecords.app.core.presentation.buttonText
import com.intensityrecords.app.core.presentation.cardTitle
import com.intensityrecords.app.core.presentation.utils.LocalAppDimens
import com.intensityrecords.app.workouts.presentation.workouts_details_screen.component.StatBadge
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.rememberWebViewStateWithHTMLData
import intensityrecordapp.intensityapp.generated.resources.Res
import intensityrecordapp.intensityapp.generated.resources._1
import intensityrecordapp.intensityapp.generated.resources.montserrat_bold
import intensityrecordapp.intensityapp.generated.resources.montserrat_regular
import intensityrecordapp.intensityapp.generated.resources.start_now
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun VideoOfTheDayCard(navController: NavController, isWideScreen: Boolean, dynamicHeight: Dp, modifier: Modifier = Modifier) {
    val dimens = LocalAppDimens.current

    // State to trigger full-screen video overlay
    var showFullScreenVideo by remember { mutableStateOf(false) }

    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val isHovered by interactionSource.collectIsHoveredAsState()
    val isActive = isFocused || isHovered

    val scale by animateFloatAsState(
        targetValue = if (isFocused) 1.10f else 1f,
        animationSpec = tween(300),
        label = "scale"
    )

    val shadowElevation by animateDpAsState(
        targetValue = if (isFocused) 14.dp else 0.dp,
        animationSpec = tween(300),
        label = "elevation"
    )

    val borderWidth = if (isFocused) dimens.borderActive else dimens.borderNormal
    val buttonElevation by animateDpAsState(if (isFocused) 22.dp else 0.dp)

    val borderBrush = if (isActive) {
        Brush.horizontalGradient(
            colorStops = arrayOf(
                0.0f to PrimaryAccent.copy(alpha = 0.4f),
                0.2f to PrimaryAccent.copy(alpha = 0.8f),
                0.5f to PrimaryAccent,
                0.8f to PrimaryAccent.copy(alpha = 0.8f),
                1.0f to PrimaryAccent.copy(alpha = 0.4f)
            )
        )
    } else {
        GlowBorderBrush
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(dynamicHeight)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .shadow(
                elevation = shadowElevation,
                shape = RoundedCornerShape(dimens.cornerRadius),
                spotColor = PrimaryAccent,
                ambientColor = PrimaryAccent,
            )
            .clip(RoundedCornerShape(dimens.cornerRadius))
            .background(CardBackground)
            .border(BorderStroke(borderWidth, borderBrush), RoundedCornerShape(dimens.cornerRadius))
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                // Open full-screen video instead of navigating
                showFullScreenVideo = true
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
                style = cardTitle.copy(
                    fontSize = dimens.titleLarge,
                    fontFamily = FontFamily(Font(Res.font.montserrat_bold))
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "Your daily 8-minute abs workout",
                style = TextStyle(
                    fontWeight = FontWeight.Medium,
                    fontSize = dimens.bodyMedium,
                    color = Color.White,
                    fontFamily = FontFamily(Font(Res.font.montserrat_regular))
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    // Open full-screen video instead of navigating
                    showFullScreenVideo = true
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
                    stringResource(Res.string.start_now),
                    style = buttonText.copy(fontSize = dimens.buttonText),
                    fontFamily = FontFamily(Font(Res.font.montserrat_bold))
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            if (isWideScreen) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    StatBadge(icon = Icons.Default.Timer, text = "Average 15-20 min")
                    Spacer(modifier = Modifier.width(24.dp))
                    StatBadge(icon = Icons.Default.LocalFireDepartment, text = "Bodyweight")
                    Spacer(modifier = Modifier.width(24.dp))
                    StatBadge(icon = Icons.Default.CenterFocusStrong, text = "Focus glutes + legs")
                }
            } else {
                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    StatBadge(icon = Icons.Default.Timer, text = "Average 15-20 min")
                    StatBadge(icon = Icons.Default.LocalFireDepartment, text = "Bodyweight")
                    StatBadge(icon = Icons.Default.CenterFocusStrong, text = "Focus glutes + legs")
                }
            }
        }
    }

    // Full-Screen Video Dialog Overlay
    if (showFullScreenVideo) {

        val closeFocusRequester = remember { FocusRequester() }
        val closeInteractionSource = remember { MutableInteractionSource() }
        val isCloseFocused by closeInteractionSource.collectIsFocusedAsState()

        // 2. Animations for the Close Button
        val closeScale by animateFloatAsState(if (isCloseFocused) 1.2f else 1f)
        val closeStrokeWidth by animateDpAsState(if (isCloseFocused) 3.dp else 0.dp)

        Dialog(
            onDismissRequest = { showFullScreenVideo = false },
            properties = DialogProperties(
                usePlatformDefaultWidth = false, // Critical: Allows the dialog to be truly full screen
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
            ) {

                // 1. Insert your actual Video Player Composable here.
                // Make sure your player is configured to autoplay upon composition.
                VideoPlayerAutoPlayPlaceholder(modifier = Modifier.fillMaxSize(), dimens = dimens)

                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(24.dp)
                        .graphicsLayer {
                            scaleX = closeScale
                            scaleY = closeScale
                        }
                        .size(56.dp) // Slightly larger for better visibility
                        .shadow(
                            elevation = if (isCloseFocused) 10.dp else 0.dp,
                            shape = CircleShape,
                            spotColor = PrimaryAccent
                        )
                        .focusRequester(closeFocusRequester)
                        .background(
                            color = if (isCloseFocused) Color.Black else Color.Black.copy(alpha = 0.5f),
                            shape = CircleShape
                        )
                        // Green border when focused
                        .border(
                            width = closeStrokeWidth,
                            color = PrimaryAccent,
                            shape = CircleShape
                        )
                        .clickable(
                            interactionSource = closeInteractionSource,
                            indication = null
                        ) {
                            showFullScreenVideo = false
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close Video",
                        tint = if (isCloseFocused) PrimaryAccent else Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun VideoPlayerAutoPlayPlaceholder(modifier: Modifier = Modifier, dimens: AppDimens) {

    var isPlaying by remember { mutableStateOf(true) }

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Text(
            text = "Video is Playing Here...",
            color = Color.White
        )
        val videoId = "Fkt6n72KGvo?si=ZGfDseeYSd4UdnHk"
        val iframeHtml = """
   <!DOCTYPE html>
    <html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
        <style>
            body, html { margin: 0; padding: 0; width: 100%; height: 100%; background-color: black; overflow: hidden; }
            .container { position: relative; width: 100%; height: 100%; }
            iframe { position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none; }
        </style>
    </head>
    <body>
        <div class="container">
            <iframe 
                src="https://www.youtube.com/embed/${videoId}?autoplay=1&mute=1&playsinline=1&controls=1&enablejsapi=1&origin=https://www.youtube.com" 
                allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" 
                allowfullscreen>
            </iframe>
        </div>
    </body>
    </html>
    """.trimIndent()

        val webViewState = rememberWebViewStateWithHTMLData(
            data = iframeHtml,
            baseUrl = "https://www.youtube.com/"
        )

        LaunchedEffect(webViewState) {
            webViewState.webSettings.apply {
                isJavaScriptEnabled = true

                // 1. Set a standard Browser User-Agent
                // This stops YouTube from treating the app like a restricted bot
                customUserAgentString = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36"
                androidWebSettings.apply {
                    domStorageEnabled = true
                    mediaPlaybackRequiresUserGesture = false
                    safeBrowsingEnabled = true
                }
            }
        }

        if (isPlaying) {
            WebView(
                state = webViewState,
                modifier = Modifier.fillMaxSize(),
            )
        } else {
            Image(
                painter = painterResource(resource = Res.drawable._1),
                contentDescription = "Video Thumbnail",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}