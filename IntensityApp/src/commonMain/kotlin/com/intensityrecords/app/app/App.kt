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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.intensityrecord.app.Screen
import com.intensityrecord.core.presentation.DarkGradient
import com.intensityrecord.core.presentation.FitnessAppTheme
import com.intensityrecords.app.core.presentation.components.AppHeader
import com.intensityrecords.app.core.presentation.components.CustomBottomBar
import com.intensityrecords.app.home.presentation.home_screen.HomeScreen
import com.intensityrecords.app.home.presentation.home_screen.VideoDetailScreen
import com.intensityrecords.app.live.presentation.live_screen.LiveScreen
import com.intensityrecords.app.mobility.presentation.mobility_screen.MobilityScreen
import com.intensityrecords.app.workouts.domain.workoutCategories
import com.intensityrecords.app.workouts.presentation.workouts_screen.WorkoutDetailScreen
import com.intensityrecords.app.workouts.presentation.workouts_screen.WorkoutScreen
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App() {
//    MaterialTheme {
//        val navController = rememberNavController()
//
//        NavHost(
//            navController = navController,
//            startDestination = Route.BookGraph
//        )
//        {
//            // ================================================================
//            // App Navigation Graph
//            // ================================================================
//            navigation<Route.ZensiGraph>(
//                startDestination = Route.Login
//            ) {
//                // --- Login ---
//                composable<Route.Login> {
//                    val viewModel = koinViewModel<LoginViewModel>()
//                    LoginViewRoot(
//                        viewModel = viewModel,
//                        onLoginSuccess = {
//                            navController.navigate(Route.Dashboard) {
//                                popUpTo(Route.Login) { inclusive = true }
//                            }
//                        },
//                        onDemoClick = {
//                            navController.navigate(Route.Demo)
//                        }
//                    )
//                }
//
//                // --- Dashboard ---
//                composable<Route.Dashboard> {
//                    val viewModel = koinViewModel<DashboardViewModel>()
//                    DashboardScreenRoot(
//                        viewModel = viewModel,
//                        onNavigateToSensorSettings = { padId ->
//                            navController.navigate(Route.SensorSettings(padId))
//                        },
//                        onNavigateToAppSettings = {
//                            navController.navigate(Route.AppSettings)
//                        }
//                    )
//                }
//
//                // --- Sensor Settings ---
//                composable<Route.SensorSettings>(
//                    enterTransition = {
//                        slideInHorizontally(
//                            initialOffsetX = { it },
//                            animationSpec = tween(300, easing = FastOutSlowInEasing)
//                        )
//                    },
//                    exitTransition = {
//                        slideOutHorizontally(
//                            targetOffsetX = { it },
//                            animationSpec = tween(300, easing = FastOutSlowInEasing)
//                        )
//                    }
//                ) { entry ->
//                    val args = entry.toRoute<Route.SensorSettings>()
//                    val viewModel = koinViewModel<SensorSettingsViewModel> {
//                        parametersOf(args.padId)
//                    }
//                    SensorSettingsScreenRoot(
//                        viewModel = viewModel,
//                        onBackClick = { navController.navigateUp() },
//                        onCalibrateClick = { padId ->
//                            navController.navigate(Route.Calibrate(padId))
//                        },
//                        onChartClick = { sensorName ->
//                            navController.navigate(Route.Chart(sensorName))
//                        }
//                    )
//                }
//
//                // --- Calibrate ---
//                composable<Route.Calibrate>(
//                    enterTransition = {
//                        slideInHorizontally(
//                            initialOffsetX = { it },
//                            animationSpec = tween(300, easing = FastOutSlowInEasing)
//                        )
//                    },
//                    exitTransition = {
//                        slideOutHorizontally(
//                            targetOffsetX = { it },
//                            animationSpec = tween(300, easing = FastOutSlowInEasing)
//                        )
//                    }
//                ) { entry ->
//                    val args = entry.toRoute<Route.Calibrate>()
//                    val viewModel = koinViewModel<CalibrateViewModel> {
//                        parametersOf(args.padId)
//                    }
//                    CalibrateScreenRoot(
//                        viewModel = viewModel,
//                        onFinish = { navController.navigateUp() }
//                    )
//                }
//
//                // --- Chart ---
//                composable<Route.Chart>(
//                    enterTransition = {
//                        slideInHorizontally(
//                            initialOffsetX = { it },
//                            animationSpec = tween(300, easing = FastOutSlowInEasing)
//                        )
//                    },
//                    exitTransition = {
//                        slideOutHorizontally(
//                            targetOffsetX = { it },
//                            animationSpec = tween(300, easing = FastOutSlowInEasing)
//                        )
//                    }
//                ) { entry ->
//                    val args = entry.toRoute<Route.Chart>()
//                    val viewModel = koinViewModel<ChartViewModel> {
//                        parametersOf(args.sensorName)
//                    }
//                    ChartScreenRoot(
//                        viewModel = viewModel,
//                        onBackClick = { navController.navigateUp() }
//                    )
//                }
//
//                // --- App Settings ---
//                composable<Route.AppSettings>(
//                    enterTransition = {
//                        slideInHorizontally(
//                            initialOffsetX = { it },
//                            animationSpec = tween(300, easing = FastOutSlowInEasing)
//                        )
//                    },
//                    exitTransition = {
//                        slideOutHorizontally(
//                            targetOffsetX = { it },
//                            animationSpec = tween(300, easing = FastOutSlowInEasing)
//                        )
//                    }
//                ) {
//                    val viewModel = koinViewModel<AppSettingsViewModel>()
//                    AppSettingsScreenRoot(
//                        viewModel = viewModel,
//                        onBackClick = { navController.navigateUp() },
//                        onLogout = {
//                            navController.navigate(Route.Login) {
//                                popUpTo(Route.ZensiGraph) { inclusive = true }
//                            }
//                        }
//                    )
//                }
//
//                // --- Demo ---
//                composable<Route.Demo> {
//                    val viewModel = koinViewModel<DemoViewModel>()
//                    DemoScreenRoot(
//                        viewModel = viewModel,
//                        onBackClick = { navController.navigateUp() }
//                    )
//                }
//            }
//
//            // ================================================================
//            // Book Navigation Graph (reference architecture)
//            // ================================================================
//            navigation<Route.BookGraph>(
//                startDestination = Route.BookList
//            ) {
//                composable<Route.BookList>(
//                    exitTransition = {
//                        slideOutHorizontally(
//                            targetOffsetX = { -it },
//                            animationSpec = tween(300, easing = FastOutSlowInEasing)
//                        )
//                    },
//                    popEnterTransition = {
//                        slideInHorizontally(
//                            initialOffsetX = { -it },
//                            animationSpec = tween(300, easing = FastOutSlowInEasing)
//                        )
//                    }
//                ) {
//                    val viewModel = koinViewModel<BookListViewModel>()
//                    val selectedBookViewModel = it.sharedKoinViewModel<SelectedBookViewModel>(navController)
//
//                    LaunchedEffect(true) {
//                        selectedBookViewModel.onSelectBook(null)
//                    }
//
//                    BookListScreenRoot(
//                        viewModel = viewModel,
//                        onBookClick = { book ->
//                            selectedBookViewModel.onSelectBook(book)
//                            navController.navigate(Route.BookDetail(book.id))
//                        }
//                    )
//                }
//
//                composable<Route.BookDetail>(
//                    enterTransition = { slideInHorizontally { it } },
//                    exitTransition = { slideOutHorizontally { it } }
//                ) {
//                    val selectedBookViewModel = it.sharedKoinViewModel<SelectedBookViewModel>(navController)
//                    val viewModel = koinViewModel<BookDetailViewModel>()
//                    val selectedBook by selectedBookViewModel.selectedBook.collectAsStateWithLifecycle()
//
//                    LaunchedEffect(selectedBook) {
//                        selectedBook?.let { book ->
//                            viewModel.onAction(BookDetailAction.OnSelectedBookChange(book))
//                        }
//                    }
//
//                    BookDetailScreenRoot(
//                        viewModel = viewModel,
//                        onBackClick = { navController.navigateUp() }
//                    )
//                }
//            }
//        }
//    }

    FitnessAppTheme {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .background(DarkGradient)
        )
        {
            val screenWidth = maxWidth
            val isWideScreen = screenWidth > 600.dp
            val animationDuration = if (isWideScreen) 500 else 300

            val navController = rememberNavController()
            val currentBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = currentBackStackEntry?.destination?.route

            val currentTab = when {
                currentRoute == Screen.Home.route -> "Home"
                currentRoute == Screen.Live.route -> "Live"
//                        Screen.WorkOuts.route -> "Workouts"
                currentRoute == Screen.WorkOuts.route || currentRoute?.startsWith("details/") == true -> "Workouts"
                currentRoute == Screen.Mobility.route -> "Mobility"
                else -> "Home"
            }

            Scaffold(
                containerColor = Color.Transparent,
                modifier = Modifier.windowInsetsPadding(WindowInsets.safeDrawing),
                topBar = {
                    AppHeader()
                },
                bottomBar = {
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
            ) { innerPadding ->
                NavHost(
                    navController = navController,
                    startDestination = Screen.Home.route,
                    modifier = Modifier.padding(innerPadding)
                ) {
                    composable(
                        route = Screen.Home.route,
                        enterTransition = {
                            fadeIn(animationSpec = tween(animationDuration))
                        },
                        exitTransition = {
                            fadeOut(animationSpec = tween(animationDuration))
                        },
                        popEnterTransition = {
                            fadeIn(animationSpec = tween(animationDuration))
                        },
                        popExitTransition = {
                            fadeOut(animationSpec = tween(animationDuration))
                        }
                    ) {
                        HomeScreen(navController, isWideScreen)
                    }

                    composable(
                        route = Screen.VideoDetail.route,
                        enterTransition = { fadeIn(animationSpec = tween(animationDuration)) },
                        exitTransition = { fadeOut(animationSpec = tween(animationDuration)) },
                        popEnterTransition = {
                            fadeIn(
                                animationSpec = tween(
                                    animationDuration
                                )
                            )
                        },
                        popExitTransition = {
                            fadeOut(
                                animationSpec = tween(
                                    animationDuration
                                )
                            )
                        }
                    ) {
                        VideoDetailScreen(navController, isWideScreen)
                    }

                    composable(
                        route = Screen.Live.route,
                        enterTransition = {
                            fadeIn(animationSpec = tween(animationDuration))
                        },
                        exitTransition = {
                            fadeOut(animationSpec = tween(animationDuration))
                        },
                        popEnterTransition = {
                            fadeIn(animationSpec = tween(animationDuration))
                        },
                        popExitTransition = {
                            fadeOut(animationSpec = tween(animationDuration))
                        }
                    ) {
                        LiveScreen(navController, isWideScreen)
                    }

                    composable(
                        route = Screen.WorkOuts.route,
                        enterTransition = {
                            fadeIn(animationSpec = tween(animationDuration))
                        },
                        exitTransition = {
                            fadeOut(animationSpec = tween(animationDuration))
                        },
                        popEnterTransition = {
                            fadeIn(animationSpec = tween(animationDuration))
                        },
                        popExitTransition = {
                            fadeOut(animationSpec = tween(animationDuration))
                        }
                    ) {
                        WorkoutScreen(navController, isWideScreen)
                    }

                    composable("details/{workoutId}") { backStackEntry ->
                        // Get the ID passed from the previous screen
                        val workoutId = backStackEntry.arguments?.getString("workoutId")

                        // Find the actual object from your data list
                        val selectedItem = workoutCategories.find { it.title == workoutId }

                        // Pass it to the detail screen
                        if (selectedItem != null) {
                            WorkoutDetailScreen(navController = navController, item = selectedItem, isWideScreen = isWideScreen)
                        }
                    }

                    composable(
                        route = Screen.Mobility.route,
                        enterTransition = {
                            fadeIn(animationSpec = tween(animationDuration))
                        },
                        exitTransition = {
                            fadeOut(animationSpec = tween(animationDuration))
                        },
                        popEnterTransition = {
                            fadeIn(animationSpec = tween(animationDuration))
                        },
                        popExitTransition = {
                            fadeOut(animationSpec = tween(animationDuration))
                        }
                    ) {
                        MobilityScreen(navController, isWideScreen)
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
