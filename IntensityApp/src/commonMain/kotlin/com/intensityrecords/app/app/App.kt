package com.intensityrecords.app.app

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.intensityrecord.app.Route
import com.intensityrecord.core.presentation.DarkGradient
import com.intensityrecord.core.presentation.FitnessAppTheme
import com.intensityrecords.app.core.presentation.components.AppHeader
import com.intensityrecords.app.core.presentation.components.CustomBottomBar
import com.intensityrecords.app.core.presentation.components.TopNavigationLayout
import com.intensityrecords.app.core.presentation.utils.CompactDimens
import com.intensityrecords.app.core.presentation.utils.ExpandedDimens
import com.intensityrecords.app.core.presentation.utils.LocalAppDimens
import com.intensityrecords.app.core.presentation.utils.currentDeviceConfiguration
import com.intensityrecords.app.home.presentation.home_screen.HomeScreenRoot
import com.intensityrecords.app.home.presentation.video_detail_screen.VideoDetailScreen
import com.intensityrecords.app.live.presentation.live_screen.LiveScreenRoot
import com.intensityrecords.app.live.presentation.timetable_screen.TimeTableScreen
import com.intensityrecords.app.mobility.presentation.mobility_screen.MobilityScreenRoot
import com.intensityrecords.app.steptrip.domain.trips
import com.intensityrecords.app.steptrip.presentation.step_trip_detail_screen.StepTripDetailScreen
import com.intensityrecords.app.steptrip.presentation.step_trip_detail_screen.StepTripDetailScreenRoot
import com.intensityrecords.app.steptrip.presentation.steptrip.StepTripScreenRoot
import com.intensityrecords.app.workouts.domain.workoutCategories
import com.intensityrecords.app.workouts.presentation.workouts_details_screen.WorkoutDetailScreenRoot
import com.intensityrecords.app.workouts.presentation.workouts_screen.WorkoutScreenRoot
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App() {
    FitnessAppTheme {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .background(DarkGradient)
        )
        {
            val isWideScreen = currentDeviceConfiguration().isWideScreen
            val animationDuration = if (isWideScreen) 500 else 300
            val dimens = if (!isWideScreen) CompactDimens else ExpandedDimens

            CompositionLocalProvider(LocalAppDimens provides dimens) {
                val navController = rememberNavController()
                val currentBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = currentBackStackEntry?.destination

                val currentTab = when {
                    currentDestination?.hasRoute<Route.Home>() == true -> "Home"

                    currentDestination?.hasRoute<Route.Live>() == true ||
                            currentDestination?.hasRoute<Route.TimeTable>() == true -> "Live"

                    currentDestination?.hasRoute<Route.WorkOuts>() == true ||
                            currentDestination?.hasRoute<Route.WorkOutsDetailsScreen>() == true -> "Workouts"

                    currentDestination?.hasRoute<Route.Mobility>() == true -> "Mobility"

                    currentDestination?.hasRoute<Route.StepTrip>() == true ||
                            currentDestination?.hasRoute<Route.StepTripDetailScreen>() == true -> "Home"

                    else -> "Home"
                }

                Scaffold(
                    containerColor = Color.Transparent,
                    modifier = Modifier.windowInsetsPadding(WindowInsets.safeDrawing),
                    topBar = {
                        if (!isWideScreen) AppHeader(isWideScreen = isWideScreen) else
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .navigationBarsPadding()
                                    .padding(top = 10.dp),
                                contentAlignment = Alignment.TopCenter
                            ) {
                                TopNavigationLayout(
                                    isWideScreen = isWideScreen,
                                    currentTab = currentTab,
                                    navController = navController
                                )
                            }
                    },
                    bottomBar = {
                        if (!isWideScreen) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .navigationBarsPadding()
                                    .padding(bottom = 10.dp),
                                contentAlignment = Alignment.BottomCenter
                            ) {
                                CustomBottomBar(
                                    isWideScreen = isWideScreen,
                                    currentTab = currentTab,
                                    navController = navController
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Route.Home,
                        modifier = Modifier.padding(
                            top = innerPadding.calculateTopPadding(),
                            bottom = innerPadding.calculateBottomPadding()
                        )
                    ) {
                        composable<Route.Home>(
                            enterTransition = { fadeIn(animationSpec = tween(animationDuration)) },
                            exitTransition = { fadeOut(animationSpec = tween(animationDuration)) },
                            popEnterTransition = { fadeIn(animationSpec = tween(animationDuration)) },
                            popExitTransition = { fadeOut(animationSpec = tween(animationDuration)) }
                        ) {
                            HomeScreenRoot(navController, isWideScreen)
                        }

                        composable<Route.VideoDetail>(
                            enterTransition = { fadeIn(animationSpec = tween(animationDuration)) },
                            exitTransition = { fadeOut(animationSpec = tween(animationDuration)) },
                            popEnterTransition = { fadeIn(animationSpec = tween(animationDuration)) },
                            popExitTransition = { fadeOut(animationSpec = tween(animationDuration)) }
                        ) {
                            VideoDetailScreen(navController, isWideScreen)
                        }

                        composable<Route.Live>(
                            enterTransition = { fadeIn(animationSpec = tween(animationDuration)) },
                            exitTransition = { fadeOut(animationSpec = tween(animationDuration)) },
                            popEnterTransition = { fadeIn(animationSpec = tween(animationDuration)) },
                            popExitTransition = { fadeOut(animationSpec = tween(animationDuration)) }
                        ) {
                            LiveScreenRoot(navController, isWideScreen)
                        }

                        composable<Route.TimeTable>(
                            enterTransition = { fadeIn(animationSpec = tween(animationDuration)) },
                            exitTransition = { fadeOut(animationSpec = tween(animationDuration)) },
                            popEnterTransition = { fadeIn(animationSpec = tween(animationDuration)) },
                            popExitTransition = { fadeOut(animationSpec = tween(animationDuration)) }
                        ) {
                            TimeTableScreen(
                                navController = navController,
                                isWideScreen = isWideScreen
                            )
                        }

                        composable<Route.WorkOuts>(
                            enterTransition = { fadeIn(animationSpec = tween(animationDuration)) },
                            exitTransition = { fadeOut(animationSpec = tween(animationDuration)) },
                            popEnterTransition = { fadeIn(animationSpec = tween(animationDuration)) },
                            popExitTransition = { fadeOut(animationSpec = tween(animationDuration)) }
                        ) {
                            WorkoutScreenRoot(navController, isWideScreen)
                        }

                        // Arguments are now handled via Type-Safe object
                        composable<Route.WorkOutsDetailsScreen> { backStackEntry ->
                            val args = backStackEntry.toRoute<Route.WorkOutsDetailsScreen>()

                            // Find the actual object using the ID from the Route
                            val selectedItem = workoutCategories.find { it.title == args.id }

                            if (selectedItem != null) {
                                WorkoutDetailScreenRoot(
                                    navController = navController,
                                    workoutId = args.id,
                                    isWideScreen = isWideScreen
                                )
                            }
                        }

                        composable<Route.Mobility>(
                            enterTransition = { fadeIn(animationSpec = tween(animationDuration)) },
                            exitTransition = { fadeOut(animationSpec = tween(animationDuration)) },
                            popEnterTransition = { fadeIn(animationSpec = tween(animationDuration)) },
                            popExitTransition = { fadeOut(animationSpec = tween(animationDuration)) }
                        ) {
                            MobilityScreenRoot(navController, isWideScreen)
                        }

                        composable<Route.StepTrip>(
                            enterTransition = { fadeIn(animationSpec = tween(animationDuration)) },
                            exitTransition = { fadeOut(animationSpec = tween(animationDuration)) },
                            popEnterTransition = { fadeIn(animationSpec = tween(animationDuration)) },
                            popExitTransition = { fadeOut(animationSpec = tween(animationDuration)) }
                        ) {
                            StepTripScreenRoot(navController, isWideScreen)
                        }

                        composable<Route.StepTripDetailScreen> { backStackEntry ->
                            val args = backStackEntry.toRoute<Route.StepTripDetailScreen>()

                            // Find the actual object using the ID from the Route
                            val selectedItem = trips.find { it.title == args.id }

                            if (selectedItem != null) {
                                StepTripDetailScreenRoot(
                                    navController = navController,
                                    stepTripID = args.id,
                                    isWideScreen = isWideScreen
                                )
                            }
                        }

                    }
                }
            }
        }
    }
}

@Composable
private inline fun <reified T : ViewModel> NavBackStackEntry.sharedKoinViewModel(
    navController: NavController
): T {
    val navGraphRoute = destination.parent?.route ?: return koinViewModel<T>()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return koinViewModel(
        viewModelStoreOwner = parentEntry
    )
}
