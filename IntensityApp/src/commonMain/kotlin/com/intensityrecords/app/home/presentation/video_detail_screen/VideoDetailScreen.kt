package com.intensityrecords.app.home.presentation.video_detail_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.rounded.Layers
import androidx.compose.material.icons.rounded.Schedule
import androidx.compose.material3.Icon
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.intensityrecord.core.presentation.DarkGradient
import com.intensityrecord.core.presentation.FitnessAppTheme
import com.intensityrecord.core.presentation.PrimaryAccent
import com.intensityrecord.core.presentation.TextWhite
import com.intensityrecord.resources.Res
import com.intensityrecord.resources._1
import com.intensityrecord.resources.montserrat_regular
import com.intensityrecords.app.core.presentation.utils.LocalAppDimens
import com.multiplatform.webview.web.NativeWebView
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.WebViewFactoryParam
import com.multiplatform.webview.web.rememberWebViewState
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


            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = contentPadding, vertical = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = "VIDEO OF THE DAY",
                    style = TextStyle(
                        fontWeight = FontWeight.Normal,
                        fontSize = 28.sp,
                        letterSpacing = 1.sp,
                        fontFamily = FontFamily(Font(Res.font.montserrat_regular))
                    ),
                    color = TextWhite
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Glutes & Core  •  Intermediate  •  8 min",
                    style = TextStyle(
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        color = Color.Gray,
                        fontFamily = FontFamily(Font(Res.font.montserrat_regular))
                    )
                )

                Spacer(modifier = Modifier.height(32.dp))

                VideoPlayerArea(width = width, height = height)

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Schedule,
                        contentDescription = null,
                        tint = Color.Gray,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "8 min",
                        style = TextStyle(
                            color = Color.Gray,
                            fontSize = 14.sp,
                            fontFamily = FontFamily(Font(Res.font.montserrat_regular))
                        )
                    )

                    Spacer(modifier = Modifier.width(24.dp))

                    Icon(
                        imageVector = Icons.Rounded.Layers, // Using "Layers" as closest match to stack icon
                        contentDescription = null,
                        tint = Color.Gray,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Core Focus",
                        style = TextStyle(
                            color = Color.Gray,
                            fontSize = 14.sp,
                            fontFamily = FontFamily(Font(Res.font.montserrat_regular))
                        )
                    )
                }

                // Bottom spacing to ensure content isn't hidden behind navbar
                Spacer(modifier = Modifier.height(100.dp))
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
fun VideoPlayerArea(width: Dp, height: Dp) {
    var isPlaying by remember { mutableStateOf(false) }

    // YouTube Embed URL (Important: use /embed/ instead of /watch?v=)
    val webViewState = rememberWebViewState("https://www.youtube.com/watch?v=Fkt6n72KGvo&t=2s")

    Box(
        modifier = Modifier
            .width(width)
            .height(height)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.Black)
            .border(1.dp, PrimaryAccent, RoundedCornerShape(16.dp))
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
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable { isPlaying = true },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.PlayArrow,
                    contentDescription = "Play",
                    tint = PrimaryAccent,
                    modifier = Modifier.size(60.dp)
                )
            }
        }
    }
}