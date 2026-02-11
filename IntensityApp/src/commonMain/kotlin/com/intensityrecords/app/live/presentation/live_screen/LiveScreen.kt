package com.intensityrecords.app.live.presentation.live_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.intensityrecord.core.presentation.DarkGradient
import com.intensityrecord.core.presentation.FitnessAppTheme
import com.intensityrecords.app.live.presentation.live_screen.component.LargePlayButton
import com.intensityrecords.app.live.presentation.live_screen.component.LiveTagBadge
import com.intensityrecords.app.live.presentation.live_screen.component.MainVideoPlayerCard
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
            val contentPadding = if (isWideScreen) 58.dp else 16.dp

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = contentPadding, vertical = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.weight(1f))

                if (isWideScreen) {
                    Spacer(modifier = Modifier.height(28.dp))
                }

                LiveTagBadge()

                Spacer(modifier = Modifier.height(24.dp))

                MainVideoPlayerCard(isWideScreen)

                Spacer(modifier = Modifier.height(32.dp))

                LargePlayButton()

                Spacer(modifier = Modifier.weight(1.5f))

                Spacer(modifier = Modifier.height(80.dp))
            }

        }
    }
}