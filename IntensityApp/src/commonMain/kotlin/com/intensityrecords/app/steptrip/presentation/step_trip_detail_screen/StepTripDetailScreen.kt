package com.intensityrecords.app.steptrip.presentation.step_trip_detail_screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil3.compose.SubcomposeAsyncImage
import com.intensityrecord.core.presentation.DarkGradient
import com.intensityrecord.core.presentation.DetailTextSecondary
import com.intensityrecord.core.presentation.PrimaryAccent
import com.intensityrecords.app.steptrip.domain.StepTripItem
import com.intensityrecords.app.steptrip.presentation.step_trip_detail_screen.component.DetailStatItem
import com.intensityrecords.app.workouts.domain.WorkoutItem
import com.intensityrecords.app.workouts.presentation.workouts_details_screen.WorkOutsDetailAction
import com.intensityrecords.app.workouts.presentation.workouts_details_screen.WorkOutsDetailScreenViewModel
import com.intensityrecords.app.workouts.presentation.workouts_details_screen.WorkoutDetailScreen
import com.intensityrecords.app.workouts.presentation.workouts_screen.component.pulseAnimation
import intensityrecordapp.intensityapp.generated.resources.Res
import intensityrecordapp.intensityapp.generated.resources.montserrat_bold
import intensityrecordapp.intensityapp.generated.resources.close
import intensityrecordapp.intensityapp.generated.resources.montserrat_regular
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun StepTripDetailScreenRoot(
    navController: NavController,
    isWideScreen: Boolean,
    viewModel: StepTripsDetailScreenViewModel = koinViewModel(),
    onBackClick: () -> Unit,
    selectedBook: StepTripItem
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    StepTripDetailScreen(
        state = state,
        isWideScreen = isWideScreen,
        onAction = { action ->
            when (action) {
                StepTripDetailAction.OnBackClick -> onBackClick()
                else -> Unit
            }
            viewModel.onAction(action)
        },
        selectedBook = selectedBook
    )

//    state.item?.let {
//        StepTripDetailScreen(
//            state = state,
//            isWideScreen = isWideScreen,
//            onAction = { action ->
//                when (action) {
//                    StepTripDetailAction.OnBackClick -> navController.navigateUp()
//                    else -> Unit
//                }
//                viewModel.onAction(action)
//            }
//        )
//    }
}

@Composable
fun StepTripDetailScreen(
    state: StepTripDetailState,
    isWideScreen: Boolean,
    onAction: (StepTripDetailAction) -> Unit,
    selectedBook: StepTripItem
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkGradient)
    ) {
        // --- Scrollable Content ---
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 160.dp) // Add padding so content isn't hidden behind the fixed footer
        ) {

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = selectedBook.title,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontSize = 32.sp,
                        letterSpacing = 0.1.sp
                    ),
                    // Fix: Line height should typically be 1.2x to 1.4x the font size
                    lineHeight = 38.sp,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    // Fix: textAlign handles the internal text centering
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp),
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Tag
                Surface(
                    color = Color.Transparent,
                    border = BorderStroke(1.dp, Color.DarkGray),
                    shape = RoundedCornerShape(50)
                ) {
                    Text(
                        text = selectedBook.category,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = Color.Gray,
                            fontSize = 12.sp
                        ),
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

//            Image(
//                painter = painterResource(state.item!!.image),
//                contentDescription = "Battlefield",
//                contentScale = ContentScale.Crop,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 24.dp)
//                    .height(220.dp)
//            )
            SubcomposeAsyncImage(
                model = selectedBook.image,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
                    .padding(horizontal = 24.dp)
                    .height(220.dp),
                loading = {
                    // This box will show the shimmer animation until the image is ready
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .pulseAnimation()
                    )
                },
                error = {
                    // Optional: Show a specific icon if the image fails
                    Box(
                        modifier = Modifier.fillMaxSize().background(Color.DarkGray),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.Warning,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Column(
                modifier = Modifier.padding(horizontal = 24.dp)
            ) {
                Text(
                    text = "The Battle of Waterloo, on June 18, 1815, marked the end of the Napoleonic epic.",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 18.sp,
                        lineHeight = 26.sp,
                        fontWeight = FontWeight.Bold
                    ),
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Body Paragraph 1
                Text(
                    text = "After his return from the Island of Elba, Napoleon Bonaparte attempted to regain control of Europe. Near Wavre, south of Brussels, he faced the allied troops led by the Duke of Wellington, composed of Dutch, Belgian, and German forces, as well as those of the Prussian Marshal Blücher.",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 14.sp,
                        lineHeight = 22.sp,
                        color = DetailTextSecondary
                    ),
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Body Paragraph 2
                Text(
                    text = "From early morning, the French attacked the allied positions on the heights of Mont Saint-Jean. The farm of Hougoumont, the hamlet of La Haye Sainte, and Napoleon’s army became the most fiercely contested points.",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 14.sp,
                        lineHeight = 22.sp,
                        color = DetailTextSecondary
                    ),
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Body Paragraph 3
                Text(
                    text = "Despite the bravery of his troops and several breakthroughs, in the late afternoon the sudden arrival of the Prussian army pushed back the French — a symbolic event that sealed the defeat.",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 14.sp,
                        lineHeight = 22.sp,
                        color = DetailTextSecondary
                    ),
                )
            }
        }

        // --- Fixed Bottom Footer ---
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.8f),
                            Color.Black
                        )
                    )
                )
                .padding(horizontal = 16.dp, vertical = 24.dp)
        ) {

            Surface(
                color = PrimaryAccent.copy(alpha = 0.8f),
                shape = RoundedCornerShape(50),
                border = BorderStroke(1.dp, PrimaryAccent),
                modifier = Modifier
                    .height(56.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    DetailStatItem(icon = Icons.Default.AccessTime, text = selectedBook.duration)

                    // Vertical Divider (Subtle)
                    // Box(modifier = Modifier.width(1.dp).height(20.dp).background(Color.DarkGray))

                    DetailStatItem(icon = Icons.Default.LocationOn, text = selectedBook.distance)

                    // Box(modifier = Modifier.width(1.dp).height(20.dp).background(Color.DarkGray))

                    DetailStatItem(
                        icon = Icons.Default.LocalFireDepartment,
                        text = selectedBook.calories
                    )

                    // Menu Dots Icon
                    Icon(
                        imageVector = Icons.Default.MoreHoriz,
                        contentDescription = "More",
                        tint = Color.Black,
                        modifier = Modifier.size(24.dp)
                    )

                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { onAction(StepTripDetailAction.OnBackClick) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                ),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, Color.White.copy(alpha = 0.3f)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(
                    text = stringResource(Res.string.close),
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontSize = 16.sp,
                        letterSpacing = 1.sp
                    ),
                )
            }
        }
    }
}