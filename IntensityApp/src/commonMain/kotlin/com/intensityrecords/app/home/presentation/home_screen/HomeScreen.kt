package com.intensityrecords.app.home.presentation.home_screen

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForwardIos
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.rounded.Layers
import androidx.compose.material.icons.rounded.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.intensityrecord.app.Screen
import com.intensityrecord.core.presentation.CardBackground
import com.intensityrecord.core.presentation.DarkGradient
import com.intensityrecord.core.presentation.FitnessAppTheme
import com.intensityrecord.core.presentation.GlowBorderBrush
import com.intensityrecord.core.presentation.PrimaryAccent
import com.intensityrecord.core.presentation.TextWhite
import com.intensityrecord.resources.Res
import com.intensityrecord.resources._1
import com.intensityrecord.resources.montserrat_bold
import com.intensityrecord.resources.montserrat_regular
import com.intensityrecords.app.home.domain.HomeItem
import com.intensityrecords.app.home.domain.sampleItems
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource

@Composable
fun HomeScreen(navController: NavController, isWideScreen: Boolean) {
    FitnessAppTheme {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .background(DarkGradient)
        ) {
            val screenWidth = maxWidth
            val contentPadding = if (isWideScreen) 58.dp else 16.dp

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = contentPadding, vertical = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.height(10.dp))

                VideoOfTheDayCard(navController = navController, isWideScreen = isWideScreen)

                Spacer(modifier = Modifier.height(40.dp))

                if (isWideScreen) {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(24.dp),
                        contentPadding = PaddingValues(horizontal = 4.dp, vertical = 16.dp)
                    ) {
                        items(items = sampleItems) { item ->
                            ContentCard(
                                item = item,
                                width = 300.dp,
                                aspectRatio = 16f / 9f,
                                navController = navController
                            )
                        }
                    }
                } else {
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        sampleItems.forEach { item ->
                            ContentCard(
                                item = item,
                                width = screenWidth - 32.dp,
                                aspectRatio = 16f / 9f,
                                navController = navController
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(40.dp))

                IntroVideoButton()

                Spacer(modifier = Modifier.height(120.dp))
            }

        }
    }
}

@Composable
fun VideoOfTheDayCard(navController: NavController, isWideScreen: Boolean) {
    val height = if (isWideScreen) 260.dp else 220.dp

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

    val borderWidth = if (isActive) 3.dp else 1.dp
    // Standard elevation for depth, but NO Green spotColor (removes the "whole border" glow)
    val elevationState by animateDpAsState(if (isActive) 9.dp else 4.dp)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .shadow(
                elevation = elevationState,
                shape = RoundedCornerShape(24.dp),
                spotColor = PrimaryAccent.copy(alpha = 0.6f), // <--- MAKES IT GLOW GREEN
                ambientColor = PrimaryAccent.copy(alpha = 0.6f),
            )
            .clip(RoundedCornerShape(24.dp))
            .background(CardBackground)
            .border(BorderStroke(borderWidth, borderBrush), RoundedCornerShape(24.dp))
            .focusable(interactionSource = interactionSource)
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                navController.navigate(Screen.VideoDetail.route)
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
                    startX = 0f, endX = 1000f
                )
            )
        )

        // Text Content
        Column(modifier = Modifier.align(Alignment.CenterStart).padding(32.dp).fillMaxWidth(0.7f)) {
            Text(
                "VIDEO OF THE DAY",
                style = TextStyle(
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 24.sp,
                    fontFamily = FontFamily(Font(Res.font.montserrat_bold))
                ),
                color = TextWhite
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                "Today's Workout: Glutes & Core",
                style = TextStyle(
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    color = Color.Gray,
                    fontFamily = FontFamily(Font(Res.font.montserrat_regular))
                )
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = {
                    navController.navigate(Screen.VideoDetail.route)
                },
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryAccent),
                shape = RoundedCornerShape(50),
                modifier = Modifier.height(40.dp)
            ) {
                Text(
                    "START NOW",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(Res.font.montserrat_bold))
                )
            }
        }
    }
}


@Composable
fun ContentCard(item: HomeItem, width: Dp, aspectRatio: Float, navController: NavController) {
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

    val borderWidth = if (isActive) 2.dp else 1.dp
    val elevationState = if (isActive) 12.dp else 4.dp

    Card(
        modifier = Modifier
            .width(width)
            .height(width / aspectRatio)
            .shadow(
                elevation = elevationState,
                shape = RoundedCornerShape(16.dp),
                spotColor = PrimaryAccent.copy(alpha = 0.4f),
                ambientColor = PrimaryAccent.copy(alpha = 0.4f)
            )
            .clip(RoundedCornerShape(16.dp))
            // Spotlight Border
            .border(BorderStroke(borderWidth, borderBrush), RoundedCornerShape(16.dp))
            .focusable(interactionSource = interactionSource)
            .clickable(interactionSource = interactionSource, indication = null) {
                when (item.title) {
                    "Workout" -> {
                        navController.navigate(Screen.WorkOuts.route)
                    }

                    "Mobility" -> {
                        navController.navigate(Screen.Mobility.route)
                    }

                    "Live Class" -> {
                        navController.navigate(Screen.Live.route)
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
                item.title.uppercase(),
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    fontFamily = FontFamily(Font(Res.font.montserrat_bold))
                ),
                color = TextWhite,
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
                        color = Color.Black,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily(Font(Res.font.montserrat_bold))
                    )
                }
            }
        }
    }
}


@Composable
fun IntroVideoButton() {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    // Border Logic
    val borderBrush = if (isFocused) SolidColor(PrimaryAccent) else GlowBorderBrush
    val borderWidth = if (isFocused) 2.dp else 1.dp

    // Outer Box ensures the button is centered in the screen
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.CenterStart
    ) {
        // The Pill Button itself
        Row(
            modifier = Modifier
                .widthIn(min = 250.dp, max = 300.dp) // Constrain width (Pill shape)
                .height(64.dp)
                .shadow(8.dp, CircleShape)
                .clip(CircleShape)
                .background(Color(0xFF111111)) // Dark BG
                .border(BorderStroke(borderWidth, borderBrush), CircleShape) // Gradient Border
                .focusable(interactionSource = interactionSource)
                .clickable { /* Play */ }
                .padding(start = 8.dp, end = 24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon Circle
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(PrimaryAccent.copy(alpha = 0.8f), CircleShape)
                    .border(1.dp, PrimaryAccent, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Filled.PlayArrow, contentDescription = null, tint = Color.Black)
            }

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = "Intro Video",
                style = TextStyle(
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    color = TextWhite,
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


@Composable
fun VideoDetailScreen(navController: NavController, isWideScreen: Boolean) {
    FitnessAppTheme {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .background(DarkGradient)
        ) {
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


@Composable
fun VideoPlayerArea(width: Dp, height: Dp) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    val isHovered by interactionSource.collectIsHoveredAsState()
    val isActive = isFocused || isHovered

    val borderBrush = if (isActive && isFocused) {
        Brush.horizontalGradient(
            colorStops = arrayOf(
                0.0f to Color.Transparent,
                0.3f to Color.Transparent,
                0.45f to PrimaryAccent.copy(alpha = 0.5f),
                0.5f to PrimaryAccent,
                0.55f to PrimaryAccent.copy(alpha = 0.5f),
                0.7f to Color.Transparent,
                1.0f to Color.Transparent
            )
        )
    } else {
        GlowBorderBrush // Default subtle gradient
    }

    val borderWidth = if (isActive) 3.dp else 1.dp
    val elevationState by animateDpAsState(if (isActive) 9.dp else 4.dp)


    Box(
        modifier = Modifier
            .width(width)
            .height(height)
            .shadow(
                elevation = elevationState,
                shape = RoundedCornerShape(16.dp),
                spotColor = PrimaryAccent.copy(alpha = 0.5f),
                ambientColor = PrimaryAccent.copy(alpha = 0.2f)
            )
            .clip(RoundedCornerShape(16.dp))
            .background(CardBackground)
            .border(BorderStroke(borderWidth, borderBrush), RoundedCornerShape(16.dp))
            .focusable(interactionSource = interactionSource)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
            ) { /* Play Video */ }
    ) {
        Image(
            painter = painterResource(resource = Res.drawable._1), // Reusing existing resource
            contentDescription = "Video Thumbnail",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize().background(Color.DarkGray)
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.4f))
        )

        Box(
            modifier = Modifier.align(Alignment.Center),
            contentAlignment = Alignment.Center
        ) {
            // Dashed Circle Drawing
            Canvas(modifier = Modifier.size(80.dp)) {
                drawCircle(
                    color = PrimaryAccent,
                    style = Stroke(
                        width = 2.dp.toPx(),
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(15f, 15f), 0f)
                    )
                )
            }
            // Inner Fill
            Icon(
                imageVector = Icons.Filled.PlayArrow,
                contentDescription = "Play",
                tint = PrimaryAccent,
                modifier = Modifier.size(40.dp)
            )
        }

        // E. Bottom Center: "PLAY" Pill Button
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp)
                .width(160.dp)
                .height(48.dp)
                .clip(RoundedCornerShape(50))
                .background(Color.Black.copy(alpha = 0.6f))
                .border(1.dp, PrimaryAccent, RoundedCornerShape(50))
                .clickable { },
            contentAlignment = Alignment.Center
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.PlayArrow,
                    contentDescription = null,
                    tint = PrimaryAccent,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "PLAY",
                    style = TextStyle(
                        color = TextWhite,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        letterSpacing = 1.sp,
                        fontFamily = FontFamily(Font(Res.font.montserrat_bold))
                    )
                )
            }
        }
    }
}