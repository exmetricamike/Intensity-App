package com.intensityrecords.app.live.presentation.live_screen

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
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.rounded.AccessTime
import androidx.compose.material.icons.rounded.LocalFireDepartment
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavController
import com.intensityrecord.core.presentation.CardBackground
import com.intensityrecord.core.presentation.DarkGradient
import com.intensityrecord.core.presentation.FitnessAppTheme
import com.intensityrecord.core.presentation.GlowBorderBrush
import com.intensityrecord.core.presentation.PrimaryAccent
import com.intensityrecords.app.core.presentation.LocalAppDimens
import com.intensityrecords.app.live.presentation.live_screen.component.MainVideoPlayerCard
import intensityrecordapp.intensityapp.generated.resources.Res
import intensityrecordapp.intensityapp.generated.resources.replay
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LiveScreenRoot(
    navController: NavController,
    isWideScreen: Boolean,
    viewModel: LiveScreenViewModel = koinViewModel()
) {
    LiveScreen(
        navController = navController,
        isWideScreen = isWideScreen
    )
}

@Composable
fun LiveScreen(navController: NavController, isWideScreen: Boolean) {

    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    val isHovered by interactionSource.collectIsHoveredAsState()
    val scope = rememberCoroutineScope()

    val firstItemFocusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        firstItemFocusRequester.requestFocus()
    }

    val pagerState = rememberPagerState(pageCount = { 10 })

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

    FitnessAppTheme {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .background(DarkGradient)
        ) {
            val dimens = LocalAppDimens.current

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        horizontal = dimens.horizontalContentPadding,
                        vertical = dimens.verticalContentPadding
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

//                item {
//                    Text(
//                        text = stringResource(Res.string.live_tag),
//                        style = MaterialTheme.typography.titleLarge
//                    )
//                }

//                item {
//                    Spacer(modifier = Modifier.height(24.dp))
//                }

                item {
                    MainVideoPlayerCard(
                        isWideScreen = isWideScreen,
                        dimens = dimens,
                        navController = navController,
                        modifier = Modifier.focusRequester(firstItemFocusRequester)
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(38.dp))
                }

                item {
                    Text(
                        text = "LAST REPLAY",
                        style = MaterialTheme.typography.titleLarge.copy(fontSize = if (isWideScreen) 20.sp else 14.sp)
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(8.dp))
                }

                item {
                    LastReplayCard(isWideScreen)
                }

//                item {
//                    if (isWideScreen) {
//                        LazyRow(
//                            modifier = Modifier.padding(horizontal = if (isWideScreen) 60.dp else 10.dp)
//                                .fillMaxWidth(),
//                            horizontalArrangement = Arrangement.SpaceBetween,
//                            contentPadding = PaddingValues(horizontal = 12.dp)
//                        ) {
//                            item {
//                                LargePlayButton(
//                                    isWideScreen = isWideScreen,
//                                    modifier = Modifier.focusRequester(firstItemFocusRequester),
//                                    dimens = dimens
//                                )
//                            }
//                            item {
//                                RePlayButton(
//                                    isWideScreen = isWideScreen,
//                                    dimens = dimens
//                                )
//                            }
//                            item {
//                                AgendaButton(
//                                    onClick = { navController.navigate(Route.TimeTable) },
//                                    isWideScreen = isWideScreen,
//                                    dimens = dimens
//                                )
//                            }
//                        }
//                    } else {
//                        Row (
//                            modifier = Modifier.padding(horizontal = 4.dp),
//                            horizontalArrangement = Arrangement.spacedBy(15.dp)
//                        ) {
//                            LargePlayButton(
//                                isWideScreen = isWideScreen,
//                                modifier = Modifier.focusRequester(firstItemFocusRequester),
//                                dimens = dimens
//                            )
//                            RePlayButton(
//                                isWideScreen = isWideScreen,
//                                dimens = dimens
//                            )
//                            AgendaButton(
//                                onClick = { navController.navigate(Route.TimeTable) },
//                                isWideScreen = isWideScreen,
//                                dimens = dimens
//                            )
//                        }
//                    }
//                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }

            }
        }
    }
}


@Composable
fun LastReplayCard(isWideScreen: Boolean) {
    // Glowing border for the replay card
    val glowBrush = Brush.horizontalGradient(
        colors = listOf(
            PrimaryAccent.copy(alpha = 0.5f),
            PrimaryAccent.copy(alpha = 0.1f),
            PrimaryAccent.copy(alpha = 0.5f)
        )
    )

    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    val borderBrush = if (isFocused) SolidColor(PrimaryAccent) else GlowBorderBrush
    val borderWidth = if (isFocused) 2.dp else 1.dp

    val scale by animateFloatAsState(
        targetValue = if (isFocused) 1.05f else 1f,
        animationSpec = tween(300),
        label = "scale"
    )

    val shadowElevation by animateDpAsState(
        targetValue = if (isFocused) 6.dp else 0.dp,
        animationSpec = tween(300),
        label = "elevation"
    )

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
        modifier = Modifier
            .width(if (isWideScreen) 320.dp else 220.dp)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .shadow(
                elevation = shadowElevation,
                shape = RoundedCornerShape(16.dp),
                spotColor = PrimaryAccent,
                ambientColor = PrimaryAccent
            )
            .onSizeChanged { cardSize = it }
            .bringIntoViewRequester(bringIntoViewRequester)
            .border(BorderStroke(borderWidth, glowBrush), RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = {}
            )
            .background(CardBackground)
    ) {
        Column {
            // Video Thumbnail Area
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .background(Color.DarkGray), // Replace with your image
                contentAlignment = Alignment.Center
            ) {
                // Placeholder for your image
                Image(
                    painter = painterResource(Res.drawable.replay),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )

                // Play Button overlay
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Color.Black.copy(alpha = 0.6f))
                        .border(1.dp, PrimaryAccent, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.PlayArrow,
                        contentDescription = "Play",
                        tint = PrimaryAccent,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }

            // Bottom Info Area
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Stats Row
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Rounded.AccessTime,
                        contentDescription = null,
                        tint = PrimaryAccent,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        "30 MIN",
                        color = Color.LightGray,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.width(8.dp))
                    Text("•", color = Color.Gray, fontSize = 12.sp)
                    Spacer(modifier = Modifier.width(8.dp))

                    Icon(
                        imageVector = Icons.Rounded.LocalFireDepartment,
                        contentDescription = null,
                        tint = PrimaryAccent,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        "290 KCAL",
                        color = Color.LightGray,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Watch Now Button
                Surface(
                    color = Color.Transparent,
                    shape = RoundedCornerShape(50),
                    border = BorderStroke(1.dp, PrimaryAccent)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.PlayArrow,
                            contentDescription = null,
                            tint = PrimaryAccent,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "WATCH NOW",
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
