package com.intensityrecords.app.workouts.presentation.workouts_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.intensityrecord.app.Route
import com.intensityrecord.core.presentation.DarkGradient
import com.intensityrecord.core.presentation.FitnessAppTheme
import com.intensityrecord.resources.Res
import com.intensityrecord.resources.montserrat_bold
import com.intensityrecords.app.core.presentation.Title
import com.intensityrecords.app.core.presentation.captions
import com.intensityrecords.app.core.presentation.utils.LocalAppDimens
import com.intensityrecords.app.workouts.presentation.workouts_screen.component.WorkoutCard
import org.jetbrains.compose.resources.Font
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun WorkoutScreenRoot(
    navController: NavController,
    isWideScreen: Boolean,
    viewModel: WorkOutsScreenViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    WorkoutScreen(
        state = state,
        isWideScreen = isWideScreen,
        onAction = { action ->
            when (action) {
                is WorkOutsAction.OnWorkoutClick -> {
                    navController.navigate(Route.WorkOutsDetailsScreen(id = action.workout.title))
                }
            }
            viewModel.onAction(action)
        }
    )
}


@Composable
fun WorkoutScreen(
    state: WorkOutsState,
    isWideScreen: Boolean,
    onAction: (WorkOutsAction) -> Unit
) {
    FitnessAppTheme {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .background(DarkGradient)
        ) {
            val dimens = LocalAppDimens.current

            val columnsCount = if (isWideScreen) 3 else 2

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = dimens.horizontalContentPadding, vertical = 2.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "WORKOUT",
                    style = Title,
                    fontFamily = FontFamily(Font(Res.font.montserrat_bold))
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Choose your workout focus",
                    style = captions.copy(letterSpacing = 0.1.sp),
                    fontFamily = FontFamily(Font(Res.font.montserrat_bold))
                )

                Spacer(modifier = Modifier.height(20.dp))

                // 3. Single Unified Grid
                LazyVerticalGrid(
                    columns = GridCells.Fixed(columnsCount),
                    horizontalArrangement = Arrangement.spacedBy(25.dp),
                    verticalArrangement = Arrangement.spacedBy(45.dp),
                    // Add bottom padding here so last items aren't cut off
                    contentPadding = PaddingValues(
                        top = 16.dp,
                        bottom = 120.dp, // This replaces the external Spacer
                        start = 4.dp,
                        end = 4.dp
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxSize()
                ) {
                    items(state.workouts) { workout ->
                        WorkoutCard(
                            item = workout,
                            isWideScreen = isWideScreen,
                            onClick = { onAction(WorkOutsAction.OnWorkoutClick(workout = workout)) },
                            dimens = dimens
                        )
                    }
                }

                Spacer(modifier = Modifier.height(120.dp))

            }

        }
    }
}


