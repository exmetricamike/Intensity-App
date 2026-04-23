package com.intensityrecords.app.program.presentation.program_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.intensityrecord.core.presentation.DarkGradient
import com.intensityrecord.core.presentation.FitnessAppTheme
import com.intensityrecords.app.app.Route
import com.intensityrecords.app.core.presentation.LocalAppDimens
import com.intensityrecords.app.program.domain.ProgramCollection
import com.intensityrecords.app.program.presentation.program_screen.component.ProgramCard
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ProgramScreenRoot(
    navController: NavController,
    isWideScreen: Boolean,
    viewModel: ProgramsScreenViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ProgramScreen(
        state = state,
        isWideScreen = isWideScreen,
        onProgramClick = { collection ->
            navController.navigate(Route.ProgramDetailsScreen(collection.id))
        }
    )
}

@Composable
fun ProgramScreen(
    state: ProgramsState,
    isWideScreen: Boolean,
    onProgramClick: (ProgramCollection) -> Unit
) {
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
                    text = "MOBILITY & RECOVERY",
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Programs designed for recovery and relaxation",
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp),
                )

                Spacer(modifier = Modifier.height(20.dp))

                if (state.isLoading) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                    return@BoxWithConstraints
                } else if (!state.error.isNullOrBlank()) {
                    Box(
                        modifier = Modifier.fillMaxSize().align(Alignment.CenterHorizontally),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = state.error,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontSize = if (isWideScreen) 22.sp else 16.sp,
                                textAlign = TextAlign.Center
                            )
                        )
                    }
                } else if (state.sections.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize().align(Alignment.CenterHorizontally),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No programs available",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontSize = if (isWideScreen) 22.sp else 16.sp,
                                textAlign = TextAlign.Center
                            )
                        )
                    }
                } else {
                    val collections = state.sections.first().collections

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(columnsCount),
                        horizontalArrangement = Arrangement.spacedBy(25.dp),
                        verticalArrangement = Arrangement.spacedBy(if (isWideScreen) 45.dp else 25.dp),
                        contentPadding = PaddingValues(
                            top = 16.dp,
                            bottom = if (isWideScreen) 120.dp else 20.dp,
                            start = 4.dp,
                            end = 4.dp
                        ),
                        modifier = Modifier.fillMaxWidth().fillMaxSize()
                    ) {
                        itemsIndexed(collections) { index, program ->
                            val focusRequester = remember { FocusRequester() }

                            if (index == 0) {
                                LaunchedEffect(Unit) {
                                    focusRequester.requestFocus()
                                }
                            }

                            ProgramCard(
                                item = program,
                                isWideScreen = isWideScreen,
                                onClick = { onProgramClick(program) },
                                dimens = dimens,
                                modifier = if (index == 0) Modifier.focusRequester(focusRequester) else Modifier
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(120.dp))
            }
        }
    }
}
