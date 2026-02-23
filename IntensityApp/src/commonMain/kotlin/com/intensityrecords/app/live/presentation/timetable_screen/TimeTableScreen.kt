package com.intensityrecords.app.live.presentation.timetable_screen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.PlayCircleFilled
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.intensityrecord.core.presentation.DarkGradient
import com.intensityrecord.core.presentation.FitnessAppTheme
import com.intensityrecord.core.presentation.GlowBorderBrush
import com.intensityrecord.core.presentation.PrimaryAccent
import com.intensityrecords.app.core.presentation.Title
import com.intensityrecords.app.core.presentation.buttonText
import com.intensityrecords.app.core.presentation.captions
import com.intensityrecords.app.live.domain.DaySchedule
import com.intensityrecords.app.live.domain.ScheduleSlot
import com.intensityrecords.app.live.domain.mockSchedule
import intensityrecordapp.intensityapp.generated.resources.Res
import intensityrecordapp.intensityapp.generated.resources.montserrat_bold
import intensityrecordapp.intensityapp.generated.resources.montserrat_regular
import intensityrecordapp.intensityapp.generated.resources.back
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.stringResource

@Composable
fun TimeTableScreen(navController: NavController, isWideScreen: Boolean)    {

    // State to track which day is currently selected (by Tap on mobile or Click on TV)
    var selectedDayIndex by remember { mutableStateOf(-1) }

    // Focus Requester to handle initial TV focus
    val focusRequester = remember { FocusRequester() }

    // Request focus on the first item only if it is a TV
    LaunchedEffect(Unit) {
        if (isWideScreen) {
            focusRequester.requestFocus()
        }
    }

    val glowingLaserBrush = Brush.horizontalGradient(
        colorStops = arrayOf(
            0.0f to PrimaryAccent.copy(alpha = 0.2f),   // Left Side (Visible but dim)
            0.4f to PrimaryAccent.copy(alpha = 0.3f),
            0.48f to PrimaryAccent,                    // Center Glow Start
            0.52f to PrimaryAccent,                    // Center Glow End
            0.6f to PrimaryAccent.copy(alpha = 0.3f),
            1.0f to PrimaryAccent.copy(alpha = 0.2f)   // Right Side (Visible but dim)
        )
    )

    FitnessAppTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(DarkGradient)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 15.dp, bottom = 5.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Header
                Text(
                    text = "TIME TABLE",
                    fontFamily = FontFamily(Font(Res.font.montserrat_bold)),
                    style = Title.copy(color = Color.White)
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "LIVE",
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily(Font(Res.font.montserrat_bold))
                    )
                )

                Spacer(modifier = Modifier.height(20.dp))

                // The Schedule Grid Container
                // We use a Box with a border to mimic the large golden frame in the image
                Box(
                    modifier = Modifier
                        .fillMaxWidth(if (isWideScreen) 0.92f else 0.95f)
                        .fillMaxHeight(if (isWideScreen) 0.75f else 0.60f)
//                        .weight(1f) // Fill available space
                        .border(
                            BorderStroke(2.5.dp, glowingLaserBrush),
                            RoundedCornerShape(20.dp)
                        ).padding(if (isWideScreen) 18.dp else 12.dp),
                ) {
                    // Horizontal List of Days
                    LazyRow(
                        modifier = Modifier.fillMaxHeight(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
//                        itemsIndexed(mockSchedule) { index, day ->
//                            DayColumnItem(
//                                day = day,
//                                isWideScreen = isWideScreen,
//                                focusRequester = if (index == 0) focusRequester else null
//                            )
//                        }
                        itemsIndexed(mockSchedule) { index, day ->

                            DayColumnItem(
                                modifier = Modifier.fillParentMaxHeight(if (isWideScreen) 1f else 0.9f),
                                day = day,
                                isWideScreen = isWideScreen,
                                isSelected = (index == selectedDayIndex), // Check if this index is selected
                                focusRequester = if (index == 0) focusRequester else null,
                                onClick = {
                                    // Logic for selection
                                    selectedDayIndex = index

                                    // Log / Toast feedback
                                    println("Selected Day: ${day.dayName}")
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Bottom Buttons (Play Live & Back)
                LazyRow(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = if (isWideScreen) 100.dp else 10.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp)
                ) {

                    item {
                        // Play Live Button
                        TimeTableButton(
                            text = "PLAY LIVE",
                            icon = Icons.Default.PlayArrow,
                            isPrimary = true,
                            onClick = { /* Handle Play */ },
                            isWideScreen = isWideScreen
                        )
                    }

                    item {
                        // Back Button
                        TimeTableButton(
                            text = stringResource(Res.string.back),
                            icon = null,
                            isPrimary = false,
                            onClick = { navController.popBackStack() },
                            isWideScreen = isWideScreen
                        )
                    }

                }
            }
        }
    }
}

@Composable
fun DayColumnItem(
    modifier: Modifier = Modifier,
    day: DaySchedule,
    isWideScreen: Boolean,
    isSelected: Boolean,
    focusRequester: FocusRequester? = null,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    // Combined State for Visuals
    val isActive = isFocused || isSelected

    // 1. Scale Animation
    val scale by animateFloatAsState(
        targetValue = if (isFocused) 1.1f else if (isSelected) 1.05f else 1f,
        animationSpec = tween(300),
        label = "scale"
    )

    // 3. Border Brush Logic (Using vertical gradient for tall columns)
    val borderBrush = if (isActive) {
        Brush.verticalGradient(
            colors = listOf(
                Color.Transparent,
                PrimaryAccent.copy(alpha = 0.5f),
                PrimaryAccent, // Bright center
                PrimaryAccent.copy(alpha = 0.5f),
                Color.Transparent
            )
        )
    } else {
        SolidColor(Color.White.copy(alpha = 0.2f))
    }

    val borderWidth = if (isActive) 3.dp else 1.dp

    Column(
        modifier = modifier
            // Apply FocusRequester if provided (for the first item)
            .then(if (focusRequester != null) Modifier.focusRequester(focusRequester) else Modifier)
            // Apply Zoom Animation
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .fillMaxHeight()
            .clip(RoundedCornerShape(12.dp))
            .border(borderWidth, borderBrush, RoundedCornerShape(12.dp))
            .background(Color.Black.copy(alpha = 0.6f))
            .padding(vertical = 12.dp)
            .clickable(interactionSource = interactionSource, indication = null, onClick = onClick)
            .width(if (isWideScreen) 140.dp else 110.dp)
        ,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Day Name
        Text(
            text = day.dayName,
            style = captions.copy(
                fontFamily = FontFamily(Font(Res.font.montserrat_regular))
            ),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Slots
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 8.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            day.slots.forEach { slot ->
                SlotItem(slot)
            }
        }
    }
}

@Composable
fun SlotItem(slot: ScheduleSlot) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        // Live Badge
        if (slot.isLive) {
            Box(
                modifier = Modifier
                    .background(Color(0xFFCC3300), RoundedCornerShape(8.dp))
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.PlayCircleFilled,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(12.dp)
                    )
                    Spacer(modifier = Modifier.width(2.5.dp))
                    Text(
                        text = "NEXT LIVE",
                        style = captions.copy(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
        }

        // Time
        if (slot.time != null) {
            Text(
                text = slot.time,
                style = buttonText.copy(
                    color = if (slot.isLive) PrimaryAccent else Color.White,
                    fontFamily = FontFamily(Font(Res.font.montserrat_bold))
                )
            )
        }

        // Title/Activity
        if (slot.title != null) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = slot.title,
                style = TextStyle(
                    color = Color.White,
                    fontSize = 10.sp,
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(Font(Res.font.montserrat_regular))
                ),
                modifier = Modifier.padding(horizontal = 4.dp)
            )
        }
    }
}

@Composable
fun TimeTableButton(
    text: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector?,
    isPrimary: Boolean,
    onClick: () -> Unit,
    isWideScreen: Boolean
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    val borderColor =
        if (isFocused) PrimaryAccent else if (isPrimary) PrimaryAccent else Color.White
    val contentColor = if (isPrimary) PrimaryAccent else Color.White

    val borderBrush = if (isFocused) SolidColor(PrimaryAccent) else GlowBorderBrush
    val containerColor = if (isFocused) PrimaryAccent.copy(alpha = 0.1f) else Color.Transparent

    Row(
        modifier = Modifier
            .width(if (isWideScreen) 200.dp else 150.dp)
            .height(56.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(containerColor)
            .border(BorderStroke(2.dp, borderBrush), CircleShape)
            .clickable(interactionSource = interactionSource, indication = null, onClick = onClick)
            .padding(horizontal = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = contentColor,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
        Text(
            text = text,
            style = buttonText.copy(
                color = Color.White,
                fontFamily = FontFamily(Font(Res.font.montserrat_bold))
            ),
        )
    }
}