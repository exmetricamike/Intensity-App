package com.intensityrecords.app.home.presentation.home_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.intensityrecord.core.presentation.DarkGradient
import com.intensityrecord.core.presentation.FitnessAppTheme
import com.intensityrecords.app.home.domain.sampleItems
import com.intensityrecords.app.home.presentation.home_screen.component.ContentCard
import com.intensityrecords.app.home.presentation.home_screen.component.IntroVideoButton
import com.intensityrecords.app.home.presentation.home_screen.component.VideoOfTheDayCard
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreenRoot(
    navController: NavController,
    isWideScreen: Boolean,
    viewModel: HomeScreenViewModel = koinViewModel()
) {
    HomeScreen(
        navController = navController,
        isWideScreen = isWideScreen
    )
}

@Composable
fun HomeScreen(navController: NavController, isWideScreen: Boolean) {
    FitnessAppTheme {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .background(DarkGradient)
        ) {
            val screenWidth = maxWidth
            val contentPadding = if (isWideScreen) 58.dp else 16.dp

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = contentPadding, vertical = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.height(10.dp))

                VideoOfTheDayCard(navController = navController, isWideScreen = isWideScreen)

                Spacer(modifier = Modifier.height(40.dp))

                if (isWideScreen) {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(24.dp),
                        contentPadding = PaddingValues(horizontal = 4.dp, vertical = 16.dp)
                    ) {
                        items(items = sampleItems) { item ->
                            ContentCard(
                                item = item,
                                width = 300.dp,
                                aspectRatio = 16f / 9f,
                                navController = navController
                            )
                        }
                    }
                } else {
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        sampleItems.forEach { item ->
                            ContentCard(
                                item = item,
                                width = screenWidth - 32.dp,
                                aspectRatio = 16f / 9f,
                                navController = navController
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(40.dp))

                IntroVideoButton()

                Spacer(modifier = Modifier.height(120.dp))
            }

        }
    }
}