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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccessTime
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.intensityrecords.app.app.Route
import com.intensityrecord.core.presentation.CardBackground
import com.intensityrecord.core.presentation.GlowBorderBrush
import com.intensityrecord.core.presentation.PrimaryAccent
import com.intensityrecords.app.core.domain.AppDimens
import com.intensityrecords.app.core.presentation.components.MuxVideoPlayer
import intensityrecordapp.intensityapp.generated.resources.Res
import intensityrecordapp.intensityapp.generated.resources._4
import intensityrecordapp.intensityapp.generated.resources.live_tag
import intensityrecordapp.intensityapp.generated.resources.montserrat_bold
import intensityrecordapp.intensityapp.generated.resources.agenda
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun MainVideoPlayerCard(
    isWideScreen: Boolean,
    dimens: AppDimens,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val cardHeight = if (isWideScreen) 250.dp else 210.dp
    val cardWidth = if (isWideScreen) 600.dp else 350.dp

    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val isHovered by interactionSource.collectIsHoveredAsState()
    val isActive = isFocused || isHovered

    val borderWidth = if (isFocused) 3.dp else 1.dp

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

    val textSize = if (isWideScreen) 20.sp else 12.sp
    val surfaceHeight = if (isWideScreen) 60.dp else 40.dp

    var timeLeftInSeconds by remember { mutableStateOf(300) } // 5 minutes = 300 seconds
    var isTimerFinished by remember { mutableStateOf(false) }

    // This effect runs every time the screen is opened (enters composition)
    LaunchedEffect(Unit) {
        while (timeLeftInSeconds > 0) {
            delay(1000L) // Wait 1 second
            timeLeftInSeconds--
        }
        isTimerFinished = true
    }

//    // Format time to MM:SS safely for Kotlin Multiplatform
//    val minutes = (timeLeftInSeconds / 60).toString().padStart(2, '0')
//    val seconds = (timeLeftInSeconds % 60).toString().padStart(2, '0')
    val days = (timeLeftInSeconds / 86400).toString().padStart(2, '0')
    val hours = (timeLeftInSeconds / 3600).toString().padStart(2, '0')
    val minutes = ((timeLeftInSeconds % 3600) / 60).toString().padStart(2, '0')
    val seconds = (timeLeftInSeconds % 60).toString().padStart(2, '0')
    val timeString =
        if (days != "00") "$days : $hours : $minutes : $seconds"
        else if (hours != "00") "$hours : $minutes : $seconds"
        else "$minutes : $seconds"

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(cardHeight)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .padding(
                horizontal = if (isWideScreen) 10.dp else 4.dp,
                vertical = if (isWideScreen) 10.dp else 0.dp
            )
            .shadow(
                elevation = shadowElevation,
                shape = RoundedCornerShape(24.dp),
                spotColor = PrimaryAccent,
                ambientColor = PrimaryAccent
            )
            .clip(RoundedCornerShape(24.dp))
            .background(CardBackground)
            .border(BorderStroke(borderWidth, borderBrush), RoundedCornerShape(24.dp))
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                navController.navigate(Route.TimeTable)
            }
    ) {

        if (isTimerFinished) {
            MuxVideoPlayer(
                modifier = Modifier.fillMaxSize(),
                playbackId = "n2KvjXdPt02d5uPGwdqZo18g2ZGYjeiHwsvqzCIxIAFw"
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

//            Row(
//                modifier = Modifier
//                    .align(Alignment.BottomCenter)
//                    .padding(bottom = 24.dp),
//                verticalAlignment = Alignment.CenterVertically
//            )
//            {
//                Surface(
//                    color = PrimaryAccent.copy(alpha = 0.2f),
//                    shape = RoundedCornerShape(50),
//                    border = BorderStroke(1.dp, PrimaryAccent),
//                    modifier = Modifier.height(surfaceHeight)
//                ) {
//                    Row(
//                        verticalAlignment = Alignment.CenterVertically,
//                        horizontalArrangement = Arrangement.Center,
//                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 2.dp)
//                    ) {
//                        Icon(
//                            imageVector = Icons.Rounded.AccessTime,
//                            contentDescription = null,
//                            tint = PrimaryAccent,
//                            modifier = Modifier.size(18.dp)
//                        )
//                        Spacer(modifier = Modifier.width(8.dp))
//                        Text(
//                            text = timeString,
//                            fontFamily = FontFamily(Font(Res.font.montserrat_bold)),
//                            style = TextStyle(
//                                fontSize = textSize,
//                                color = PrimaryAccent,
//                                fontWeight = FontWeight.Bold
//                            )
//                        )
//                    }
//                }
//            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp, vertical = if (isWideScreen) 15.dp else 15.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                Text(
                    text = stringResource(Res.string.live_tag),
                    style = MaterialTheme.typography.titleLarge.copy(fontSize = if (isWideScreen) 34.sp else 24.sp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Next session starts in",
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = if (isWideScreen) 20.sp else 14.sp),
                )

                Row(
                    modifier = Modifier
                        .padding(top = if (isWideScreen) 8.dp else 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                )
                {
                    Surface(
                        color = PrimaryAccent.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(50),
                        border = BorderStroke(1.dp, PrimaryAccent),
                        modifier = Modifier.height(surfaceHeight)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.padding(
                                horizontal = if (isWideScreen) 40.dp else 24.dp,
                                vertical = 2.dp
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.AccessTime,
                                contentDescription = null,
                                tint = PrimaryAccent,
                                modifier = Modifier.size(if (isWideScreen) 26.dp else 16.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = timeString,
                                fontFamily = FontFamily(Font(Res.font.montserrat_bold)),
                                style = TextStyle(
                                    fontSize = textSize,
                                    color = PrimaryAccent,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(if (isWideScreen) 26.dp else 22.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 2.dp)
                ) {
//                    Icon(
//                        imageVector = Icons.Rounded.CalendarMonth,
//                        contentDescription = null,
//                        tint = PrimaryAccent,
//                        modifier = Modifier.size(if (isWideScreen) 22.dp else 14.dp)
//                    )
//                    Spacer(modifier = Modifier.width(if (isWideScreen) 10.dp else 8.dp))
//                    Text(
//                        text = "AGENDA",
//                        fontFamily = FontFamily(Font(Res.font.montserrat_bold)),
//                        style = TextStyle(
//                            fontSize = textSize,
//                            color = Color.White,
//                            fontWeight = FontWeight.Bold
//                        )
//                    )

                    Row(
                        modifier = modifier
                            .width(if (isWideScreen) 200.dp else 130.dp)
                            .height(if (isWideScreen) 50.dp else 45.dp)
                            .clickable(
                                interactionSource = interactionSource,
                                indication = null,
                                onClick = { navController.navigate(Route.TimeTable) }
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.CalendarMonth,
                            contentDescription = null,
                            tint = PrimaryAccent,
                            modifier = Modifier.size(if (isWideScreen) 26.dp else 16.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = stringResource(Res.string.agenda),
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontSize = dimens.agendaButton,
                                letterSpacing = 1.sp,
                            )
                        )
                    }

//                    AgendaButton(
//                        onClick = { navController.navigate(Route.TimeTable) },
//                        isWideScreen = isWideScreen,
//                        dimens = dimens,
//                        modifier = modifier
//                    )
                }
            }
        }
    }
}