package com.intensityrecords.app.live.presentation.live_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.intensityrecord.app.Route
import com.intensityrecord.core.presentation.DarkGradient
import com.intensityrecord.core.presentation.FitnessAppTheme
import com.intensityrecords.app.core.presentation.LocalAppDimens
import com.intensityrecords.app.live.presentation.live_screen.component.AgendaButton
import com.intensityrecords.app.live.presentation.live_screen.component.LargePlayButton
import com.intensityrecords.app.live.presentation.live_screen.component.MainVideoPlayerCard
import com.intensityrecords.app.live.presentation.live_screen.component.RePlayButton
import intensityrecordapp.intensityapp.generated.resources.Res
import intensityrecordapp.intensityapp.generated.resources.live_tag
import org.jetbrains.compose.resources.stringResource
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

                item {
                    Text(
                        text = stringResource(Res.string.live_tag),
                        style = MaterialTheme.typography.titleLarge
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(24.dp))
                }

                item {
                    MainVideoPlayerCard(isWideScreen)
                }

                item {
                    Spacer(modifier = Modifier.height(32.dp))
                }

                item {
                    if (isWideScreen) {
                        LazyRow(
                            modifier = Modifier.padding(horizontal = if (isWideScreen) 60.dp else 10.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            contentPadding = PaddingValues(horizontal = 12.dp)
                        ) {
                            item {
                                LargePlayButton(
                                    isWideScreen = isWideScreen,
                                    modifier = Modifier.focusRequester(firstItemFocusRequester),
                                    dimens = dimens
                                )
                            }
                            item {
                                RePlayButton(
                                    isWideScreen = isWideScreen,
                                    dimens = dimens
                                )
                            }
                            item {
                                AgendaButton(
                                    onClick = { navController.navigate(Route.TimeTable) },
                                    isWideScreen = isWideScreen,
                                    dimens = dimens
                                )
                            }
                        }
                    } else {
                        Row (
                            modifier = Modifier.padding(horizontal = 4.dp),
                            horizontalArrangement = Arrangement.spacedBy(15.dp)
                        ) {
                            LargePlayButton(
                                isWideScreen = isWideScreen,
                                modifier = Modifier.focusRequester(firstItemFocusRequester),
                                dimens = dimens
                            )
                            RePlayButton(
                                isWideScreen = isWideScreen,
                                dimens = dimens
                            )
                            AgendaButton(
                                onClick = { navController.navigate(Route.TimeTable) },
                                isWideScreen = isWideScreen,
                                dimens = dimens
                            )
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }

            }
        }
    }
}