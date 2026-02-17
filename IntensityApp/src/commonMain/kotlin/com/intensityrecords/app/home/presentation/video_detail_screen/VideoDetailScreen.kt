package com.intensityrecords.app.home.presentation.video_detail_screen

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
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.rounded.Layers
import androidx.compose.material.icons.rounded.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavController
import com.intensityrecord.app.Route
import com.intensityrecord.core.presentation.DarkGradient
import com.intensityrecord.core.presentation.FitnessAppTheme
import com.intensityrecord.core.presentation.GlowBorderBrush
import com.intensityrecord.core.presentation.PrimaryAccent
import com.intensityrecord.core.presentation.TextWhite
import com.intensityrecord.resources.Res
import com.intensityrecord.resources._1
import com.intensityrecord.resources.montserrat_bold
import com.intensityrecord.resources.montserrat_regular
import com.intensityrecords.app.core.domain.AppDimens
import com.intensityrecords.app.core.presentation.utils.LocalAppDimens
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.rememberWebViewState
import com.multiplatform.webview.web.rememberWebViewStateWithHTMLData
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource


@Composable
fun VideoDetailScreen(navController: NavController, isWideScreen: Boolean) {
    FitnessAppTheme {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .background(DarkGradient)
        ) {

            val dimens = LocalAppDimens.current

            val screenWidth = maxWidth

            val contentPadding = if (isWideScreen) 58.dp else 16.dp
            val width = if (isWideScreen) 800.dp else screenWidth - 32.dp
            val height = if (isWideScreen) width / (16f / 9f) else 350.dp
            val textSize = if (isWideScreen) 14.sp else 10.sp
            val surfaceHeight = if (isWideScreen) 45.dp else 30.dp

            // 1. Set up Focus Requester for the Video Area
            val videoFocusRequester = remember { FocusRequester() }

            LaunchedEffect(Unit) {
                videoFocusRequester.requestFocus()
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = contentPadding, vertical = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // Header Text
                item {
                    Text(
                        text = "VIDEO OF THE DAY",
                        style = TextStyle(
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = dimens.titleLarge,
                            fontFamily = FontFamily(Font(Res.font.montserrat_bold))
                        ),
                        color = TextWhite
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                }

                // Video Player Area (Now Focusable)
                item {
                    VideoPlayerArea(
                        width = width,
                        height = height,
                        dimens = dimens,
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                }

                // Stats Info
                item {
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
                            Text("8 MIN", color = PrimaryAccent, fontSize = textSize, fontWeight = FontWeight.Bold, fontFamily = FontFamily(Font(Res.font.montserrat_bold)))
                            Text(" | ABS | ", color = Color.White, fontSize = textSize, fontWeight = FontWeight.Bold, fontFamily = FontFamily(Font(Res.font.montserrat_regular)))
                            Text("180 KCAL", color = Color.White, fontSize = textSize, fontWeight = FontWeight.Bold, fontFamily = FontFamily(Font(Res.font.montserrat_regular)))
                        }
                    }
                }
            }
        }
    }
}


//@Composable
//fun VideoPlayerArea(width: Dp, height: Dp) {
//    val interactionSource = remember { MutableInteractionSource() }
//    val isFocused by interactionSource.collectIsFocusedAsState()
//
//    val isHovered by interactionSource.collectIsHoveredAsState()
//    val isActive = isFocused || isHovered
//
//    val borderBrush = if (isActive && isFocused) {
//        Brush.horizontalGradient(
//            colorStops = arrayOf(
//                0.0f to Color.Transparent,
//                0.3f to Color.Transparent,
//                0.45f to PrimaryAccent.copy(alpha = 0.5f),
//                0.5f to PrimaryAccent,
//                0.55f to PrimaryAccent.copy(alpha = 0.5f),
//                0.7f to Color.Transparent,
//                1.0f to Color.Transparent
//            )
//        )
//    } else {
//        GlowBorderBrush // Default subtle gradient
//    }
//
//    val borderWidth = if (isActive) 3.dp else 1.dp
//    val elevationState by animateDpAsState(if (isActive) 9.dp else 4.dp)
//
//
//    Box(
//        modifier = Modifier
//            .width(width)
//            .height(height)
//            .shadow(
//                elevation = elevationState,
//                shape = RoundedCornerShape(16.dp),
//                spotColor = PrimaryAccent.copy(alpha = 0.5f),
//                ambientColor = PrimaryAccent.copy(alpha = 0.2f)
//            )
//            .clip(RoundedCornerShape(16.dp))
//            .background(CardBackground)
//            .border(BorderStroke(borderWidth, borderBrush), RoundedCornerShape(16.dp))
//            .focusable(interactionSource = interactionSource)
//            .clickable(
//                interactionSource = interactionSource,
//                indication = null,
//            ) { /* Play Video */ }
//    ) {
//        Image(
//            painter = painterResource(resource = Res.drawable._1), // Reusing existing resource
//            contentDescription = "Video Thumbnail",
//            contentScale = ContentScale.Crop,
//            modifier = Modifier.fillMaxSize().background(Color.DarkGray)
//        )
//
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(Color.Black.copy(alpha = 0.4f))
//        )
//
//        Box(
//            modifier = Modifier.align(Alignment.Center),
//            contentAlignment = Alignment.Center
//        ) {
//            // Dashed Circle Drawing
//            Canvas(modifier = Modifier.size(80.dp)) {
//                drawCircle(
//                    color = PrimaryAccent,
//                    style = Stroke(
//                        width = 2.dp.toPx(),
//                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(15f, 15f), 0f)
//                    )
//                )
//            }
//            // Inner Fill
//            Icon(
//                imageVector = Icons.Filled.PlayArrow,
//                contentDescription = "Play",
//                tint = PrimaryAccent,
//                modifier = Modifier.size(40.dp)
//            )
//        }
//
//        // E. Bottom Center: "PLAY" Pill Button
//        Box(
//            modifier = Modifier
//                .align(Alignment.BottomCenter)
//                .padding(bottom = 32.dp)
//                .width(160.dp)
//                .height(48.dp)
//                .clip(RoundedCornerShape(50))
//                .background(Color.Black.copy(alpha = 0.6f))
//                .border(1.dp, PrimaryAccent, RoundedCornerShape(50))
//                .clickable { },
//            contentAlignment = Alignment.Center
//        ) {
//            Row(verticalAlignment = Alignment.CenterVertically) {
//                Icon(
//                    imageVector = Icons.Filled.PlayArrow,
//                    contentDescription = null,
//                    tint = PrimaryAccent,
//                    modifier = Modifier.size(20.dp)
//                )
//                Spacer(modifier = Modifier.width(8.dp))
//                Text(
//                    text = "PLAY",
//                    style = TextStyle(
//                        color = TextWhite,
//                        fontWeight = FontWeight.Bold,
//                        fontSize = 16.sp,
//                        letterSpacing = 1.sp,
//                        fontFamily = FontFamily(Font(Res.font.montserrat_bold))
//                    )
//                )
//            }
//        }
//    }
//}


@Composable
fun VideoPlayerArea(width: Dp, height: Dp,dimens: AppDimens) {
    var isPlaying by remember { mutableStateOf(false) }

    // Focus Tracking
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
        targetValue = if (isFocused) 10.dp else 0.dp,
        animationSpec = tween(300),
        label = "elevation"
    )

    val borderWidth = if (isFocused) dimens.borderWidthActive else dimens.borderWidthNormal

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


    // Use iframe HTML with a proper baseUrl so the WebView sends a valid
    // Referer header — YouTube error 153 occurs when Referer is missing/invalid
    val iframeHtml = """
    <html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="referrer" content="strict-origin-when-cross-origin">
    </head>
    <style>
        * { margin: 0; padding: 0; }
        body { background-color: black; overflow: hidden; }
        .container { position: relative; width: 100%; height: 100%; }
        iframe { position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: none; }
    </style>
    <body>
        <div class="container">
            <iframe 
                src="https://www.youtube.com/embed/Fkt6n72KGvo?si=TS6HeV-lj6Jvk-LR" 
                frameborder="0" 
                allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" 
                allowfullscreen
                referrerpolicy="strict-origin-when-cross-origin">
            </iframe>
        </div>
    </body>
    </html>
    """.trimIndent()

    // Load with baseUrl so WebView sends a proper Referer header to YouTube
    val webViewState = rememberWebViewStateWithHTMLData(
        data = iframeHtml,
        baseUrl = "https://www.youtube.com"
    )

    // Enable JavaScript and DOM storage (required for YouTube player)
    webViewState.webSettings.apply {
        isJavaScriptEnabled = true
        androidWebSettings.apply {
            domStorageEnabled = true
            mediaPlaybackRequiresUserGesture = false
        }
    }

    Box(
        modifier = Modifier
            .width(width)
            .height(height)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .onSizeChanged { cardSize = it }
            // 5. Attach the requester (MUST be before clickable/focusable)
            .bringIntoViewRequester(bringIntoViewRequester)
            .shadow(
                elevation = shadowElevation,
                shape = RoundedCornerShape(dimens.cardCornerRadius),
                spotColor = PrimaryAccent,
                ambientColor = PrimaryAccent
            )
            .clip(RoundedCornerShape(16.dp))
            .background(Color.Black)
            .border(BorderStroke(borderWidth, borderBrush), RoundedCornerShape(16.dp))
            .clickable(interactionSource = interactionSource, indication = null) {
                isPlaying = true
            }
            .focusable(interactionSource = interactionSource)
    ) {
        if (isPlaying) {
            // This single component works for both Android and iOS
            WebView(
                state = webViewState,
                modifier = Modifier.fillMaxSize(),
            )
        } else {
            // --- YOUR EXISTING THUMBNAIL UI ---
            Image(
                painter = painterResource(resource = Res.drawable._1),
                contentDescription = "Video Thumbnail",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // Play Button Overlay
//            Box(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .clickable { isPlaying = true },
//                contentAlignment = Alignment.Center
//            ) {
//                Icon(
//                    imageVector = Icons.Filled.PlayArrow,
//                    contentDescription = "Play",
//                    tint = PrimaryAccent,
//                    modifier = Modifier.size(60.dp)
//                )
//            }

            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 32.dp)
                    .clickable { isPlaying = true},
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Button(
                        onClick = { isPlaying = true },
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryAccent),
                        shape = RoundedCornerShape(24),
                        modifier = Modifier.height(dimens.buttonHeight)
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
    }
}