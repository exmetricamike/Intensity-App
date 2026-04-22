package com.intensityrecords.app.steptrip.presentation.step_trip_detail_screen

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
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
import com.intensityrecords.app.core.presentation.utils.LocalAppLocale
import com.intensityrecords.app.steptrip.domain.StepTripItem
import com.intensityrecords.app.steptrip.domain.category
import com.intensityrecords.app.steptrip.domain.description
import com.intensityrecords.app.steptrip.domain.title
import com.intensityrecords.app.steptrip.presentation.step_trip_detail_screen.component.DetailStatItem
import com.intensityrecords.app.workouts.presentation.workouts_screen.component.pulseAnimation
import intensityrecordapp.intensityapp.generated.resources.Res
import intensityrecordapp.intensityapp.generated.resources.close
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
}

@Composable
fun StepTripDetailScreen(
    state: StepTripDetailState,
    isWideScreen: Boolean,
    onAction: (StepTripDetailAction) -> Unit,
    selectedBook: StepTripItem
) {
    val locale = LocalAppLocale.current
    val uriHandler = LocalUriHandler.current

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
                .padding(bottom = 200.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = selectedBook.title(locale),
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontSize = 32.sp,
                        letterSpacing = 0.1.sp
                    ),
                    lineHeight = 38.sp,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp),
                )

                Spacer(modifier = Modifier.height(8.dp))

                Surface(
                    color = Color.Transparent,
                    border = BorderStroke(1.dp, Color.DarkGray),
                    shape = RoundedCornerShape(50)
                ) {
                    Text(
                        text = selectedBook.category(locale)?.uppercase() ?: "",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = Color.Gray,
                            fontSize = 12.sp
                        ),
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            SubcomposeAsyncImage(
                model = selectedBook.coverImage,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp)
                    .height(220.dp),
                loading = {
                    Box(modifier = Modifier.fillMaxSize().pulseAnimation())
                },
                error = {
                    Box(
                        modifier = Modifier.fillMaxSize().background(Color.DarkGray),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Warning, contentDescription = null, tint = Color.White)
                    }
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            selectedBook.description(locale)?.let { desc ->
                Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                    Text(
                        text = desc,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontSize = 14.sp,
                            lineHeight = 22.sp,
                            color = DetailTextSecondary
                        ),
                    )
                }
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
                    selectedBook.durationMin?.let {
                        DetailStatItem(icon = Icons.Default.AccessTime, text = "$it min")
                    }
                    selectedBook.distanceKm?.let {
                        DetailStatItem(icon = Icons.Default.LocationOn, text = "${it.trimEnd('0').trimEnd('.')} km")
                    }
                    selectedBook.caloriesBurned?.let {
                        DetailStatItem(icon = Icons.Default.LocalFireDepartment, text = "$it kcal")
                    }
                    Icon(
                        imageVector = Icons.Default.MoreHoriz,
                        contentDescription = "More",
                        tint = Color.Black,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            if (selectedBook.mapsUrl != null) {
                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = { uriHandler.openUri(selectedBook.mapsUrl) },
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryAccent),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text(
                        text = "OPEN ROUTE",
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = Color.Black,
                            fontSize = 16.sp,
                            letterSpacing = 1.sp
                        ),
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = { onAction(StepTripDetailAction.OnLetsGoClick) },
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryAccent),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(
                    text = "LET'S GO",
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = Color.Black,
                        fontSize = 16.sp,
                        letterSpacing = 1.sp
                    ),
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = { onAction(StepTripDetailAction.OnBackClick) },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
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
