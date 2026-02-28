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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.intensityrecord.app.Route
import com.intensityrecord.core.presentation.DarkGradient
import com.intensityrecord.core.presentation.FitnessAppTheme
import com.intensityrecords.app.core.presentation.LocalAppDimens
import com.intensityrecords.app.workouts.presentation.workouts_screen.component.WorkoutCard
import intensityrecordapp.intensityapp.generated.resources.Res
import intensityrecordapp.intensityapp.generated.resources.choose_workout_focus
import intensityrecordapp.intensityapp.generated.resources.workout
import org.jetbrains.compose.resources.stringResource
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
    // 1. Create a FocusRequester
    val firstItemFocusRequester = remember { FocusRequester() }

    // 2. Trigger focus request when screen loads or when list is ready
    LaunchedEffect(state.workouts) {
        if (state.workouts.isNotEmpty()) {
            firstItemFocusRequester.requestFocus()
        }
    }

    FitnessAppTheme {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .background(DarkGradient)
        ) {
            val dimens = LocalAppDimens.current

            val columnsCount = if (isWideScreen) 3 else 1

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = dimens.horizontalContentPadding, vertical = 2.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = stringResource(Res.string.workout).uppercase(),
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = stringResource(Res.string.choose_workout_focus),
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp),
                )

                Spacer(modifier = Modifier.height(20.dp))

                // 3. Single Unified Grid
                LazyVerticalGrid(
                    columns = GridCells.Fixed(columnsCount),
                    horizontalArrangement = Arrangement.spacedBy(25.dp),
                    verticalArrangement = Arrangement.spacedBy(if (isWideScreen) 45.dp else 25.dp),
                    // Add bottom padding here so last items aren't cut off
                    contentPadding = PaddingValues(
                        top = 16.dp,
                        bottom = if (isWideScreen) 120.dp else 20.dp, // This replaces the external Spacer
                        start = 4.dp,
                        end = 4.dp
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxSize()
                ) {
                    items(state.workouts.size) { index ->
                        val workout = state.workouts[index]
                        WorkoutCard(
                            item = workout,
                            isWideScreen = isWideScreen,
                            onClick = { onAction(WorkOutsAction.OnWorkoutClick(workout = workout)) },
                            dimens = dimens,
                            modifier = if (index == 0) {
                                Modifier.focusRequester(firstItemFocusRequester)
                            } else {
                                Modifier
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(120.dp))

            }

        }
    }
}


