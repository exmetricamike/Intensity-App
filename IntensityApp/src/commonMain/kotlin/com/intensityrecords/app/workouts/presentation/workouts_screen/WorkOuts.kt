package com.intensityrecords.app.workouts.presentation.workouts_screen

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
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CenterFocusStrong
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.intensityrecord.core.presentation.CardBackground
import com.intensityrecord.core.presentation.DarkGradient
import com.intensityrecord.core.presentation.FitnessAppTheme
import com.intensityrecord.core.presentation.GlowBorderBrush
import com.intensityrecord.core.presentation.PrimaryAccent
import com.intensityrecords.app.workouts.domain.Session
import com.intensityrecords.app.workouts.domain.WorkoutItem
import com.intensityrecords.app.workouts.domain.workoutCategories
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import com.intensityrecord.resources.Res
import com.intensityrecord.resources.montserrat_bold
import com.intensityrecord.resources.montserrat_regular

@Composable
fun WorkoutScreen(navController: NavController, isWideScreen: Boolean) {
    FitnessAppTheme {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .background(DarkGradient)
        ) {
            val contentPadding = if (isWideScreen) 58.dp else 16.dp

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = contentPadding, vertical = 14.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "WORKOUT",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp,
                    fontFamily = FontFamily(Font(Res.font.montserrat_bold))
                )

                Spacer(modifier = Modifier.height(20.dp))

                if (isWideScreen) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        horizontalArrangement = Arrangement.spacedBy(22.dp),
                        verticalArrangement = Arrangement.spacedBy(20.dp),
                        contentPadding = PaddingValues(horizontal = 4.dp, vertical = 16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(workoutCategories) { workout ->
                            WorkoutCard(
                                item = workout,
                                isWideScreen = isWideScreen,
                                onClick = {
                                    // Important: Encode title or use ID if titles have spaces/symbols
                                    navController.navigate("details/${workout.title}")
                                }
                            )
                        }
                    }
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(1),
                        horizontalArrangement = Arrangement.spacedBy(22.dp),
                        verticalArrangement = Arrangement.spacedBy(20.dp),
                        contentPadding = PaddingValues(horizontal = 4.dp, vertical = 16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(workoutCategories) { workout ->
                            WorkoutCard(
                                item = workout,
                                isWideScreen = isWideScreen,
                                onClick = {
                                    // Important: Encode title or use ID if titles have spaces/symbols
                                    navController.navigate("details/${workout.title}")
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(40.dp))

            }

        }
    }
}

@Composable
fun WorkoutCard(
    item: WorkoutItem,
    modifier: Modifier = Modifier,
    isWideScreen: Boolean,
    onClick: () -> Unit
) {
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

    val borderWidth = if (isFocused) 3.dp else 1.dp
    val elevationState by animateDpAsState(if (isActive) 6.5.dp else 2.dp)

    val aspectRatioForCard = if (isWideScreen) {
        1.2f
    } else {
        16f / 9f
    }

    Card(
        modifier = modifier
            .aspectRatio(aspectRatioForCard)
            .shadow(
                elevation = elevationState,
                shape = RoundedCornerShape(16.dp),
                spotColor = PrimaryAccent.copy(alpha = 0.4f),
                ambientColor = PrimaryAccent.copy(alpha = 0.4f)
            )
            .clip(RoundedCornerShape(16.dp))
            .border(BorderStroke(borderWidth, borderBrush), RoundedCornerShape(16.dp))
            .focusable(interactionSource = interactionSource)
            .clickable(interactionSource = interactionSource, indication = null, onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
                .background(Color(0xFF1A1A1A))
        ) {
            Image(
                painter = painterResource(item.image),
                contentDescription = item.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize().alpha(0.7f)
            )

            Column(modifier = Modifier.padding(12.dp)) {
                Surface(
                    color = Color.Black.copy(alpha = 0.6f),
                    shape = RoundedCornerShape(4.dp),
                    border = BorderStroke(0.5.dp, PrimaryAccent.copy(alpha = 0.5f))
                ) {
                    Text(
                        text = item.duration,
                        color = PrimaryAccent,
                        fontSize = 10.sp,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily(Font(Res.font.montserrat_regular))
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Surface(
                    color = Color.Black.copy(alpha = 0.6f),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        text = item.level,
                        color = Color.LightGray,
                        fontSize = 9.sp,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                        fontFamily = FontFamily(Font(Res.font.montserrat_regular))
                    )
                }
            }

            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = item.title,
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = FontFamily(Font(Res.font.montserrat_bold))
                )
                Spacer(modifier = Modifier.height(14.dp))
                Surface(
                    color = PrimaryAccent.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(50),
                    border = BorderStroke(1.dp, PrimaryAccent)
                ) {
                    Text(
                        text = item.duration,
                        color = PrimaryAccent,
                        fontSize = 10.sp,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 2.dp),
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily(Font(Res.font.montserrat_regular))
                    )
                }
            }

        }
    }
}


@Composable
fun WorkoutDetailScreen(
    navController: NavController,
    isWideScreen: Boolean,
    item: WorkoutItem,
) {
    // Generate dummy sessions based on the passed item image for visual consistency
    val sessions = remember {
        List(5) { index ->
            Session(
                id = index,
                title = "${item.title} #${index + 1}",
                duration = "20 min",
                level = "Medium",
                image = item.image
            )
        }
    }

    val contentPadding = if (isWideScreen) 58.dp else 16.dp

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black) // or DarkGradient
            .verticalScroll(rememberScrollState())
    ) {
        // Background Image Faded (Global background ambiance)
//        Image(
//            painter = painterResource(item.image),
//            contentDescription = null,
//            contentScale = ContentScale.Crop,
//            modifier = Modifier.fillMaxSize().alpha(0.15f)
//        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = contentPadding, vertical = 16.dp)
        ) {
            HeroSection(item = item, isWideScreen = isWideScreen)

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "This month sessions",
                color = Color.White,
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(Res.font.montserrat_regular)),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                contentPadding = PaddingValues(horizontal = 4.dp, vertical = 8.dp)
            ) {
                items(sessions) { session ->
                    SessionCard(session = session)
                }
            }
        }
    }
}

@Composable
fun HeroSection(item: WorkoutItem, isWideScreen: Boolean) {

    val height = if (isWideScreen) 380.dp else 450.dp


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

    val borderWidth = if (isFocused) 3.dp else 1.dp
    val elevationState by animateDpAsState(if (isActive) 6.5.dp else 2.dp)


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .shadow(
                elevation = elevationState,
                shape = RoundedCornerShape(24.dp),
                spotColor = PrimaryAccent.copy(alpha = 0.4f),
                ambientColor = PrimaryAccent.copy(alpha = 0.4f)
            )
            .clip(RoundedCornerShape(24.dp))
            .border(BorderStroke(borderWidth, borderBrush), RoundedCornerShape(24.dp))
            .focusable(interactionSource = interactionSource)
            .clickable(interactionSource = interactionSource, indication = null) { },
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(height) // Adjust height to match screenshot
//                .border(
//                    border = BorderStroke(
//                        2.dp, Brush.horizontalGradient(
//                            colors = listOf(PrimaryAccent, PrimaryAccent.copy(alpha = 0.5f))
//                        )
//                    ),
//                    shape = RoundedCornerShape(24.dp)
//                )
                .clip(RoundedCornerShape(24.dp))
                .background(Color.Black.copy(alpha = 0.6f))
        )
        {
            Row(modifier = Modifier.fillMaxSize())
            {
                Spacer(modifier = Modifier.weight(1f))
                Box(modifier = Modifier.weight(1.2f).fillMaxHeight()) {
                    Image(
                        painter = painterResource(item.image),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.horizontalGradient(
                                    colors = listOf(Color.Black, Color.Transparent),
                                    startX = 0f,
                                    endX = 500f
                                )
                            )
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(25.dp),
                verticalArrangement = Arrangement.Center
            ) {

                val customFontSize = if (isWideScreen) 48.sp else 35.sp

                Text(
                    text = item.title.uppercase(),
                    color = Color(0xFFF0F0F0), // Off-white
                    fontSize = customFontSize,
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = FontFamily(Font(Res.font.montserrat_bold)),
                    letterSpacing = 1.sp,
                )

                Spacer(modifier = Modifier.height(12.dp))


                Text(
                    text = "12 sessions this month · ${item.duration} · ${item.level} / High",
                    color = Color.LightGray,
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(Res.font.montserrat_regular))
                )

                Spacer(modifier = Modifier.height(30.dp))

                if (isWideScreen) {
                    // CTA Button
                    Button(
                        onClick = { /* Handle coach selection */ },
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryAccent),
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp)
                    ) {
                        Text(
                            text = "LET THE COACH CHOOSE",
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(Res.font.montserrat_bold))
                        )
                    }
                } else {
                    Button(
                        onClick = { /* Handle coach selection */ },
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryAccent),
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp)
                    ) {
                        Text(
                            text = "LET THE COACH CHOOSE",
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(Res.font.montserrat_bold))
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Surprise workout selected by your coach",
                    color = Color.Gray,
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(Res.font.montserrat_regular))
                )

                Spacer(modifier = Modifier.height(30.dp))

                if (isWideScreen) {
                    // Stats Row
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        StatBadge(icon = Icons.Default.Timer, text = "Average ${item.duration}")
                        Spacer(modifier = Modifier.width(24.dp))
                        StatBadge(
                            icon = Icons.Default.LocalFireDepartment,
                            text = "High calorie burn"
                        )
                        Spacer(modifier = Modifier.width(24.dp))
                        StatBadge(
                            icon = Icons.Default.CenterFocusStrong,
                            text = "Focus glutes + legs"
                        )
                    }
                } else {
                    Column(
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        StatBadge(icon = Icons.Default.Timer, text = "Average ${item.duration}")
                        StatBadge(
                            icon = Icons.Default.LocalFireDepartment,
                            text = "High calorie burn"
                        )
                        StatBadge(
                            icon = Icons.Default.CenterFocusStrong,
                            text = "Focus glutes + legs"
                        )
                    }
                }
            }
        }

    }
}

@Composable
fun StatBadge(icon: ImageVector, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = PrimaryAccent,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            color = Color.White,
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(Res.font.montserrat_regular))
        )
    }
}

@Composable
fun SessionCard(session: Session) {
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


    Column(modifier = Modifier.width(280.dp)) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9f)
                .shadow(
                    elevation = elevationState,
                    shape = RoundedCornerShape(12.dp),
                    spotColor = PrimaryAccent.copy(alpha = 0.4f),
                    ambientColor = PrimaryAccent.copy(alpha = 0.4f)
                )
                .border(BorderStroke(borderWidth, borderBrush), RoundedCornerShape(12.dp))
//                .border(
//                    width = if (isSelected) 3.dp else 0.dp,
//                    color = if (isSelected) PrimaryAccent else Color.Transparent,
//                    shape = RoundedCornerShape(12.dp)
//                )
                .clip(RoundedCornerShape(12.dp))
                .focusable(interactionSource = interactionSource)
                .clickable(interactionSource = interactionSource, indication = null) { },
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E))
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(session.image),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize().alpha(0.7f)
                )

                // Play Button Overlay
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(12.dp)
                        .size(32.dp)
                        .background(
                            PrimaryAccent.copy(alpha = 0.8f),
                            shape = RoundedCornerShape(50)
                        )
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Play",
                        tint = Color.Black,
                        modifier = Modifier.align(Alignment.Center).size(20.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = session.title,
            color = if (isSelected) Color.White else Color.LightGray,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily(Font(Res.font.montserrat_bold))
        )
        Text(
            text = "${session.duration} · ${session.level}",
            color = Color.Gray,
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(Res.font.montserrat_regular))
        )
    }
}