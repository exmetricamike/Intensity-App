package com.intensityrecords.app.live.presentation.live_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.intensityrecord.app.Route
import com.intensityrecord.core.presentation.DarkGradient
import com.intensityrecord.core.presentation.FitnessAppTheme
import com.intensityrecords.app.core.presentation.Title
import com.intensityrecords.app.core.presentation.utils.LocalAppDimens
import com.intensityrecords.app.live.presentation.live_screen.component.AgendaButton
import com.intensityrecords.app.live.presentation.live_screen.component.LargePlayButton
import com.intensityrecords.app.live.presentation.live_screen.component.MainVideoPlayerCard
import intensityrecordapp.intensityapp.generated.resources.Res
import intensityrecordapp.intensityapp.generated.resources.montserrat_bold
import intensityrecordapp.intensityapp.generated.resources.live_tag
import org.jetbrains.compose.resources.Font
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
                        fontFamily = FontFamily(Font(Res.font.montserrat_bold)),
                        style = Title
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
                    LazyRow(
                        modifier = Modifier.padding(horizontal = if (isWideScreen) 100.dp else 10.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        contentPadding = PaddingValues(horizontal = 8.dp)
                    ) {
                        item {
                            LargePlayButton(isWideScreen = isWideScreen)
                        }
                        item {
                            AgendaButton(
                                onClick = { navController.navigate(Route.TimeTable) },
                                isWideScreen = isWideScreen
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