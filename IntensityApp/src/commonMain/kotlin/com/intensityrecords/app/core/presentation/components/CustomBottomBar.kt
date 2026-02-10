package com.intensityrecords.app.core.presentation.components

//@Composable
//fun CustomBottomBar(
//    isWideScreen: Boolean,
//    currentTab: String = "Home",
//    navController: NavController
//) {
//    // Width & Background Color adjustment
//    val barWidth = if (isWideScreen) 500.dp else 380.dp
//
//    Box(
//        modifier = Modifier
//            .width(barWidth)
//            .height(60.dp)
//            .shadow(20.dp, CircleShape)
//            .clip(CircleShape)
//            .background(Color(0xFF050505).copy(alpha = 0.95f)) // Very Dark, matching image
//    ) {
//        Row(
//            modifier = Modifier.fillMaxSize(),
//            horizontalArrangement = Arrangement.SpaceEvenly,
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            val navItems = listOf(
//                Triple("Home", Icons.Rounded.Home, true),
//                Triple("Live", Icons.Rounded.FiberManualRecord, false),
//                Triple("Workouts", Icons.Rounded.FitnessCenter, false),
//                Triple("Mobility", Icons.Rounded.SelfImprovement, false)
//            )
//
//            navItems.forEach { (label, icon, _) ->
//                val interactionSource = remember { MutableInteractionSource() }
//                val isFocused by interactionSource.collectIsFocusedAsState()
//
//                // Logic: Is this item active (Selected OR Focused)?
//                val isActive = (currentTab == label) || isFocused
//
//                // Colors:
//                // 1. Icon: Green if active, Gray if inactive
//                // 2. Text: White if active, Gray if inactive (so unselected text doesn't distract)
//                val iconColor = if (isActive) PrimaryAccent else Color.Gray
//                val textColor = if (isActive) TextWhite else Color.Gray
//
//                Row(
//                    modifier = Modifier
//                        .clip(CircleShape)
//                        .clickable(
//                            interactionSource = interactionSource,
//                            indication = null
//                        ) {
//                            val route = when (label) {
//                                "Live" -> Screen.Live.route
//                                "Home" -> Screen.Home.route
//                                "Workouts" -> Screen.WorkOuts.route
//                                "Mobility" -> Screen.Mobility.route
//                                else -> Screen.Home.route
//                            }
//                            navController.navigate(route) {
//                                popUpTo(navController.graph.findStartDestination().id) {
//                                    saveState = true
//                                }
//                                launchSingleTop = true
//                                restoreState = false
//                            }
//                        }
//                        .focusable(interactionSource = interactionSource)
//                        .padding(horizontal = 12.dp, vertical = 8.dp),
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.Center
//                ) {
//                    Icon(
//                        imageVector = icon,
//                        contentDescription = label,
//                        tint = iconColor,
//                        modifier = Modifier.size(20.dp)
//                    )
//
//                    // Spacer and Text are now OUTSIDE the 'if' block, so they always show.
//                    Spacer(modifier = Modifier.width(8.dp))
//
//                    Text(
//                        text = label,
//                        style = TextStyle(
//                            fontSize = 12.sp,
//                            fontWeight = if (isActive) FontWeight.Bold else FontWeight.Medium,
//                            color = textColor // Applies White if selected, Gray if not
//                            ,
//                            fontFamily = FontFamily(Font(Res.font.montserrat_regular))
//                        )
//                    )
//                }
//            }
//        }
//    }
//}

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.intensityrecord.app.Screen
import com.intensityrecord.core.presentation.PrimaryAccent
import com.intensityrecord.core.presentation.TextWhite
import com.intensityrecord.resources.Res
import com.intensityrecord.resources.montserrat_regular
import org.jetbrains.compose.resources.Font

@Composable
fun CustomBottomBar(
    isWideScreen: Boolean,
    currentTab: String = "Home",
    navController: NavController
) {
    val barWidth = if (isWideScreen) 500.dp else 380.dp
    val textSize = if (isWideScreen) 12.sp else 11.sp
    val iconSize = if (isWideScreen) 20.dp else 16.dp

    Box(
        modifier = Modifier
            .width(barWidth)
            .height(60.dp)
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
                Triple("Home", Icons.Rounded.Home, true),
                Triple("Live", Icons.Rounded.FiberManualRecord, false),
                Triple("Workouts", Icons.Rounded.FitnessCenter, false),
                Triple("Mobility", Icons.Rounded.SelfImprovement, false)
            )

            navItems.forEach { (label, icon, _) ->
                val interactionSource = remember { MutableInteractionSource() }
                val isFocused by interactionSource.collectIsFocusedAsState()

                val isActive = (currentTab == label) || isFocused

                val iconColor = if (isActive) PrimaryAccent else Color.Gray
                val textColor = if (isActive) TextWhite else Color.Gray

                val glowBrush = Brush.horizontalGradient(
                    colorStops = arrayOf(
                        0.0f to Color.Transparent,
                        0.2f to Color.Transparent,
                        0.4f to PrimaryAccent.copy(alpha = 0.6f),
                        0.5f to PrimaryAccent,
                        0.6f to PrimaryAccent.copy(alpha = 0.6f),
                        0.8f to Color.Transparent,
                        1.0f to Color.Transparent
                    )
                )

                val lineWidth = if (isActive) 50.dp else 0.dp
                val elevationState by animateDpAsState(if (isActive) 10.dp else 0.dp)

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .height(60.dp)
                        .clip(RectangleShape)
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null
                        ) {
                            val route = when (label) {
                                "Live" -> Screen.Live.route
                                "Home" -> Screen.Home.route
                                "Workouts" -> Screen.WorkOuts.route
                                "Mobility" -> Screen.Mobility.route
                                else -> Screen.Home.route
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
                        .padding(horizontal = 4.dp)
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(horizontal = 12.dp)
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

                    if (isActive) {
                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .width(lineWidth)
                                .height(3.dp)
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
        }
    }
}