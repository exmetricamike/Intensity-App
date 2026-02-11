package com.intensityrecords.app.workouts.presentation.workouts_details_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.intensityrecord.resources.Res
import com.intensityrecord.resources.montserrat_regular
import com.intensityrecords.app.workouts.presentation.workouts_details_screen.component.HeroSection
import com.intensityrecords.app.workouts.presentation.workouts_details_screen.component.SessionCard
import org.jetbrains.compose.resources.Font
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun WorkoutDetailScreenRoot(
    workoutId: String,
    navController: NavController,
    isWideScreen: Boolean,
    viewModel: WorkOutsDetailScreenViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    // Initialize state based on the passed ID
    LaunchedEffect(workoutId) {
        viewModel.initialize(workoutId)
    }

    state.item?.let { workoutItem ->
        WorkoutDetailScreen(
            state = state,
            isWideScreen = isWideScreen,
            onAction = { action ->
                when (action) {
                    WorkOutsDetailAction.OnBackClick -> navController.navigateUp()
                    else -> Unit
                }
                viewModel.onAction(action)
            }
        )
    }
}

@Composable
fun WorkoutDetailScreen(
    state: WorkOutsDetailState,
    isWideScreen: Boolean,
    onAction: (WorkOutsDetailAction) -> Unit,
) {

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
            state.item?.let {
                HeroSection(item = it, isWideScreen = isWideScreen)
            }

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
                items(state.sessions) { session ->
                    SessionCard(session = session)
                }
            }
        }
    }
}



