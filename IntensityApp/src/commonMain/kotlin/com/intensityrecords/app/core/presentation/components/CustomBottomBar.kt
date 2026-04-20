package com.intensityrecords.app.core.presentation.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FiberManualRecord
import androidx.compose.material.icons.rounded.FitnessCenter
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.SelfImprovement
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.intensityrecords.app.app.Route
import com.intensityrecord.core.presentation.CardBackground
import com.intensityrecord.core.presentation.PrimaryAccent
import com.intensityrecord.core.presentation.TextWhite
import com.intensityrecords.app.core.presentation.LanguageViewModel
import intensityrecordapp.intensityapp.generated.resources.Res
import intensityrecordapp.intensityapp.generated.resources.montserrat_regular
import intensityrecordapp.intensityapp.generated.resources.home
import intensityrecordapp.intensityapp.generated.resources.live
import intensityrecordapp.intensityapp.generated.resources.workouts
import intensityrecordapp.intensityapp.generated.resources.mobility
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.stringResource

@Composable
fun CustomBottomBar(
    isWideScreen: Boolean,
    currentTab: String = "Home",
    navController: NavController,
    viewModel: LanguageViewModel
) {
    val barModifier = if (isWideScreen) Modifier.fillMaxWidth(0.90f) else Modifier.fillMaxWidth(0.98f)
    val textSize = if (isWideScreen) 18.sp else 10.sp
    val iconSize = if (isWideScreen) 35.dp else 16.dp
    val barHeight = if (isWideScreen) 80.dp else 60.dp

    var showLanguageMenu by remember { mutableStateOf(false) }

    val currentLangCode by viewModel.languageCode.collectAsStateWithLifecycle()

    val selectedLanguage = remember(currentLangCode) {
        when (currentLangCode.take(2).lowercase()) {
            "fr" -> "🇫🇷"
            "nl" -> "🇳🇱"
            else -> "🇬🇧"
        }
    }


    Box(
        modifier = barModifier
            .height(barHeight)
            .shadow(20.dp, CircleShape)
            .clip(CircleShape)
            .background(Color(0xFF050505).copy(alpha = 0.95f))
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val navItems = listOf(
                Triple(stringResource(Res.string.home), Icons.Rounded.Home, true),
                Triple(stringResource(Res.string.live), Icons.Rounded.FiberManualRecord, false),
                Triple(stringResource(Res.string.workouts), Icons.Rounded.FitnessCenter, false),
                Triple(stringResource(Res.string.mobility), Icons.Rounded.SelfImprovement, false)
            )

            navItems.forEach { (label, icon, _) ->
                val interactionSource = remember { MutableInteractionSource() }
                val isFocused by interactionSource.collectIsFocusedAsState()

                val isActive = (currentTab == label) || isFocused

                val isSelected = currentTab == label

                // 2. isHighlighted: True if Selected OR Focused. (Controls Color/Visibility of Text)
                val isHighlighted = isSelected || isFocused

                val iconColor = if (isHighlighted) PrimaryAccent else Color.LightGray
                val textColor = if (isHighlighted) TextWhite else Color.LightGray

                // Optional: Add a slight scale effect when focused on TV so user knows where they are
                val scale by animateFloatAsState(if (isFocused) 1.1f else 1.0f)

                val glowBrush = Brush.horizontalGradient(
                    colorStops = arrayOf(
                        0.0f to PrimaryAccent.copy(alpha = 0.4f),
                        0.2f to PrimaryAccent.copy(alpha = 0.8f),
                        0.5f to PrimaryAccent,           // Center Bright Core
                        0.8f to PrimaryAccent.copy(alpha = 0.8f),
                        1.0f to PrimaryAccent.copy(alpha = 0.4f)
                    )
                )

                val lineWidth = if (isActive) if (isWideScreen) 100.dp else 50.dp else 0.dp
                val elevationState by animateDpAsState(if (isActive) 10.dp else 0.dp)

                val home = stringResource(Res.string.home)
                val live = stringResource(Res.string.live)
                val workouts = stringResource(Res.string.workouts)
                val mobility = stringResource(Res.string.mobility)

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .weight(1f)
                        .height(barHeight)
                        .clip(RectangleShape)
                        .scale(scale) // Apply scale animation for TV focus
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null
                        ) {
                            val route = when (label) {
                                live -> Route.Live
                                home -> Route.Home
                                workouts -> Route.WorkOuts
                                mobility -> Route.Mobility
                                else -> Route.Home
                            }
                            navController.navigate(route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = false
                            }
                        }
                        .focusable(interactionSource = interactionSource)
                        .padding(horizontal = 1.dp)
                ) {

                    if (isWideScreen) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        ) {
                            Icon(
                                imageVector = icon,
                                contentDescription = label,
                                tint = iconColor,
                                modifier = Modifier.size(iconSize)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = label,
                                style = TextStyle(
                                    fontSize = textSize,
                                    fontWeight = if (isActive) FontWeight.Bold else FontWeight.Medium,
                                    color = textColor,
                                    fontFamily = FontFamily(Font(Res.font.montserrat_regular))
                                )
                            )
                        }
                    } else {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier.padding(horizontal = 2.dp)
                        ) {
                            Icon(
                                imageVector = icon,
                                contentDescription = label,
                                tint = iconColor,
                                modifier = Modifier.size(iconSize)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = label,
                                style = TextStyle(
                                    fontSize = textSize,
                                    fontWeight = if (isActive) FontWeight.Bold else FontWeight.Medium,
                                    color = textColor,
                                    fontFamily = FontFamily(Font(Res.font.montserrat_regular))
                                )
                            )
                        }
                    }

                    if (isSelected) {
                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .width(lineWidth)
                                .height(if (isWideScreen) 4.dp else 3.dp)
                                .padding(bottom = 1.dp)
                                .shadow(
                                    elevation = elevationState,
                                    shape = RoundedCornerShape(2.dp),
                                    spotColor = PrimaryAccent,
                                    ambientColor = PrimaryAccent
                                )
                                .clip(RoundedCornerShape(2.dp))
                                .background(glowBrush)
                        )
                    }
                }
            }

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
            val borderWidth = if (isFocused) 2.dp else 1.dp

            val borderBrush = if (isActive) {
                PrimaryAccent
            } else {
                Color.Transparent
            }

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .width(60.dp)
                    .height(60.dp)
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                    }
                    .shadow(
                        elevation = shadowElevation,
                        shape = RoundedCornerShape(60.dp),
                        spotColor = PrimaryAccent,
                        ambientColor = PrimaryAccent
                    )
                    .clip(RoundedCornerShape(60.dp))
                    .background(if (isFocused) CardBackground else Color.Transparent)
                    .border(BorderStroke(borderWidth, borderBrush), RoundedCornerShape(60.dp))
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null // Removes default ripple to use our custom scale/bg
                    ) { showLanguageMenu = true }
                    .padding(horizontal = 8.dp)
            ) {
                // Renders the currently selected flag emoji
                Text(
                    text = selectedLanguage,
                    fontSize = if (isWideScreen) 24.sp else 16.sp
                )

                // Dropdown Menu for Language Selection
                DropdownMenu(
                    expanded = showLanguageMenu,
                    onDismissRequest = { showLanguageMenu = false },
                    modifier = Modifier.background(Color(0xFF1E1E1E).copy(alpha = 0.95f))
                ) {
                    DropdownMenuItem(
                        text = { Text("🇬🇧 English", color = TextWhite) },
                        onClick = {
                            showLanguageMenu = false
                            viewModel.switchLanguage("en")
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("🇫🇷 Français", color = TextWhite) },
                        onClick = {
                            showLanguageMenu = false
                            viewModel.switchLanguage("fr")
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("🇳🇱 Nederlands", color = TextWhite) },
                        onClick = {
                            showLanguageMenu = false
                            viewModel.switchLanguage("nl")
                        }
                    )
                }
            }
        }
    }
}