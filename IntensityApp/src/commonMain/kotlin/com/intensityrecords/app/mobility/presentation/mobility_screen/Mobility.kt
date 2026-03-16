package com.intensityrecords.app.mobility.presentation.mobility_screen

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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.intensityrecord.core.presentation.DarkGradient
import com.intensityrecord.core.presentation.FitnessAppTheme
import com.intensityrecords.app.core.presentation.LocalAppDimens
import com.intensityrecords.app.mobility.domain.mobilityCategories
import com.intensityrecords.app.mobility.presentation.mobility_screen.component.MobilityCard
import intensityrecordapp.intensityapp.generated.resources.Res
import intensityrecordapp.intensityapp.generated.resources.mobility_recovery
import intensityrecordapp.intensityapp.generated.resources.montserrat_bold
import intensityrecordapp.intensityapp.generated.resources.programs_recovery_relaxation
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun MobilityScreenRoot(
    navController: NavController,
    isWideScreen: Boolean,
    viewModel: MobilityScreenViewModel = koinViewModel()
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    MobilityScreen(
        state = state,
        onAction = { action ->
            when (action) {
                MobilityAction.LoadMobility -> {

                }

                is MobilityAction.OnClick -> {

                }
            }
        },
        navController = navController,
        isWideScreen = isWideScreen
    )
}

@Composable
fun MobilityScreen(
    state: MobilityState,
    onAction: (MobilityAction) -> Unit,
    navController: NavController,
    isWideScreen: Boolean
) {

    val firstItemFocusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        firstItemFocusRequester.requestFocus()
    }

    FitnessAppTheme {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .background(DarkGradient)
        ) {

            val dimens = LocalAppDimens.current

            if (state.loading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "Loading...",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontSize = if (isWideScreen) 22.sp else 16.sp,
                            textAlign = TextAlign.Center
                        )
                    )
                }
                return@BoxWithConstraints
            }

            if (!state.error.isNullOrEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
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
                return@BoxWithConstraints
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = dimens.horizontalContentPadding, vertical = 2.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = stringResource(Res.string.mobility_recovery),
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = stringResource(Res.string.programs_recovery_relaxation),
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp),
                )

                Spacer(modifier = Modifier.height(20.dp))

                val gridWidth = if (isWideScreen) 0.85f else 1f

                LazyVerticalGrid(
                    columns = GridCells.Fixed(if (isWideScreen) 2 else 1),
                    horizontalArrangement = Arrangement.spacedBy(if (isWideScreen) 25.dp else 16.dp),
                    verticalArrangement = Arrangement.spacedBy(if (isWideScreen) 45.dp else 25.dp),
                    modifier = Modifier.fillMaxWidth(gridWidth).weight(1f),
                    contentPadding = PaddingValues(
                        top = 16.dp,
                        bottom = if (isWideScreen) 120.dp else 20.dp, // This replaces the external Spacer
                        start = 4.dp,
                        end = 4.dp
                    )
                ) {
                    items(state.mobilityData.size) { item ->
                        val mobilityItem = state.mobilityData[item]
                        MobilityCard(
                            item = mobilityItem,
                            isWideScreen = isWideScreen,
                            dimens = dimens,
                            modifier = if (item == 0) {
                                Modifier.focusRequester(firstItemFocusRequester)
                            } else {
                                Modifier
                            }
                        )
                    }
                }
            }
        }
    }
}