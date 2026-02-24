package com.intensityrecords.app.home.presentation.home_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.intensityrecord.core.presentation.DarkGradient
import com.intensityrecord.core.presentation.FitnessAppTheme
import com.intensityrecords.app.core.presentation.utils.LocalAppDimens
import com.intensityrecords.app.home.presentation.home_screen.component.ContentCard
import com.intensityrecords.app.home.presentation.home_screen.component.IntroVideoButton
import com.intensityrecords.app.home.presentation.home_screen.component.VideoOfTheDayCard
import intensityrecordapp.intensityapp.generateds.app.home.domain.sampleItems
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

            // 1. Grab the exact dimensions of the current screen
            val screenWidth = maxWidth
            val screenHeight = maxHeight

            // 2. Calculate dynamic sizes based on percentages
            // E.g., Card width is 22% of the screen width (fits ~4 cards comfortably + hinting the next one)
            val dynamicCardWidth = if (isWideScreen) screenWidth * 0.22f else screenWidth - 32.dp

            // Hero card height takes up 45% of the screen height on TV
            val dynamicHeroHeight = if (isWideScreen) screenHeight * 0.60f else 250.dp

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(
                        horizontal = dimens.horizontalContentPadding,
                        vertical = dimens.verticalContentPadding
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.height(screenHeight * 0.02f)) // 2% dynamic spacer

                VideoOfTheDayCard(
                    navController = navController,
                    isWideScreen = isWideScreen,
                    dynamicHeight = dynamicHeroHeight,
                    modifier = Modifier.focusRequester(firstItemFocusRequester)
                )

                Spacer(modifier = Modifier.height(screenHeight * 0.05f)) // 5% dynamic spacer

                if (isWideScreen) {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(horizontal = 36.dp, vertical = 16.dp)
                    ) {
                        items(items = sampleItems) { item ->
//                            Box(modifier = Modifier.padding(12.dp)) {
                            ContentCard(
                                item = item,
                                width = dynamicCardWidth,
                                aspectRatio = 1.3f,
                                navController = navController,
                                dimens = dimens,
                                isWideScreen = isWideScreen,
                                modifier = Modifier.padding(12.dp)
                            )
//                            }
                        }
                    }
                } else {
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        sampleItems.forEach { item ->
                            ContentCard(
                                item = item,
                                width = dynamicCardWidth,
                                aspectRatio = 3.3f,
                                navController = navController,
                                dimens = dimens,
                                isWideScreen = isWideScreen
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(40.dp))

                IntroVideoButton()

                Spacer(modifier = Modifier.height(screenHeight * 0.15f))
            }
        }
    }
}