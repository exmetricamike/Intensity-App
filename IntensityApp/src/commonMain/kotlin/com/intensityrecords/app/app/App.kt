package com.intensityrecord.app

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.intensityrecord.auth.presentation.LoginViewModel
import com.intensityrecord.auth.presentation.LoginViewRoot
import com.intensityrecord.book.presentation.SelectedBookViewModel
import com.intensityrecord.book.presentation.book_detail.BookDetailAction
import com.intensityrecord.book.presentation.book_detail.BookDetailScreenRoot
import com.intensityrecord.book.presentation.book_detail.BookDetailViewModel
import com.intensityrecord.book.presentation.book_list.BookListScreenRoot
import com.intensityrecord.book.presentation.book_list.BookListViewModel
import com.intensityrecord.sensor.presentation.app_settings.AppSettingsScreenRoot
import com.intensityrecord.sensor.presentation.app_settings.AppSettingsViewModel
import com.intensityrecord.sensor.presentation.calibrate.CalibrateScreenRoot
import com.intensityrecord.sensor.presentation.calibrate.CalibrateViewModel
import com.intensityrecord.sensor.presentation.chart.ChartScreenRoot
import com.intensityrecord.sensor.presentation.chart.ChartViewModel
import com.intensityrecord.sensor.presentation.dashboard.DashboardScreenRoot
import com.intensityrecord.sensor.presentation.dashboard.DashboardViewModel
import com.intensityrecord.sensor.presentation.demo.DemoScreenRoot
import com.intensityrecord.sensor.presentation.demo.DemoViewModel
import com.intensityrecord.sensor.presentation.sensor_settings.SensorSettingsScreenRoot
import com.intensityrecord.sensor.presentation.sensor_settings.SensorSettingsViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
@Preview
fun App() {
    MaterialTheme {
        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = Route.BookGraph
        ) {
            // ================================================================
            // App Navigation Graph
            // ================================================================
            navigation<Route.ZensiGraph>(
                startDestination = Route.Login
            ) {
                // --- Login ---
                composable<Route.Login> {
                    val viewModel = koinViewModel<LoginViewModel>()
                    LoginViewRoot(
                        viewModel = viewModel,
                        onLoginSuccess = {
                            navController.navigate(Route.Dashboard) {
                                popUpTo(Route.Login) { inclusive = true }
                            }
                        },
                        onDemoClick = {
                            navController.navigate(Route.Demo)
                        }
                    )
                }

                // --- Dashboard ---
                composable<Route.Dashboard> {
                    val viewModel = koinViewModel<DashboardViewModel>()
                    DashboardScreenRoot(
                        viewModel = viewModel,
                        onNavigateToSensorSettings = { padId ->
                            navController.navigate(Route.SensorSettings(padId))
                        },
                        onNavigateToAppSettings = {
                            navController.navigate(Route.AppSettings)
                        }
                    )
                }

                // --- Sensor Settings ---
                composable<Route.SensorSettings>(
                    enterTransition = {
                        slideInHorizontally(
                            initialOffsetX = { it },
                            animationSpec = tween(300, easing = FastOutSlowInEasing)
                        )
                    },
                    exitTransition = {
                        slideOutHorizontally(
                            targetOffsetX = { it },
                            animationSpec = tween(300, easing = FastOutSlowInEasing)
                        )
                    }
                ) { entry ->
                    val args = entry.toRoute<Route.SensorSettings>()
                    val viewModel = koinViewModel<SensorSettingsViewModel> {
                        parametersOf(args.padId)
                    }
                    SensorSettingsScreenRoot(
                        viewModel = viewModel,
                        onBackClick = { navController.navigateUp() },
                        onCalibrateClick = { padId ->
                            navController.navigate(Route.Calibrate(padId))
                        },
                        onChartClick = { sensorName ->
                            navController.navigate(Route.Chart(sensorName))
                        }
                    )
                }

                // --- Calibrate ---
                composable<Route.Calibrate>(
                    enterTransition = {
                        slideInHorizontally(
                            initialOffsetX = { it },
                            animationSpec = tween(300, easing = FastOutSlowInEasing)
                        )
                    },
                    exitTransition = {
                        slideOutHorizontally(
                            targetOffsetX = { it },
                            animationSpec = tween(300, easing = FastOutSlowInEasing)
                        )
                    }
                ) { entry ->
                    val args = entry.toRoute<Route.Calibrate>()
                    val viewModel = koinViewModel<CalibrateViewModel> {
                        parametersOf(args.padId)
                    }
                    CalibrateScreenRoot(
                        viewModel = viewModel,
                        onFinish = { navController.navigateUp() }
                    )
                }

                // --- Chart ---
                composable<Route.Chart>(
                    enterTransition = {
                        slideInHorizontally(
                            initialOffsetX = { it },
                            animationSpec = tween(300, easing = FastOutSlowInEasing)
                        )
                    },
                    exitTransition = {
                        slideOutHorizontally(
                            targetOffsetX = { it },
                            animationSpec = tween(300, easing = FastOutSlowInEasing)
                        )
                    }
                ) { entry ->
                    val args = entry.toRoute<Route.Chart>()
                    val viewModel = koinViewModel<ChartViewModel> {
                        parametersOf(args.sensorName)
                    }
                    ChartScreenRoot(
                        viewModel = viewModel,
                        onBackClick = { navController.navigateUp() }
                    )
                }

                // --- App Settings ---
                composable<Route.AppSettings>(
                    enterTransition = {
                        slideInHorizontally(
                            initialOffsetX = { it },
                            animationSpec = tween(300, easing = FastOutSlowInEasing)
                        )
                    },
                    exitTransition = {
                        slideOutHorizontally(
                            targetOffsetX = { it },
                            animationSpec = tween(300, easing = FastOutSlowInEasing)
                        )
                    }
                ) {
                    val viewModel = koinViewModel<AppSettingsViewModel>()
                    AppSettingsScreenRoot(
                        viewModel = viewModel,
                        onBackClick = { navController.navigateUp() },
                        onLogout = {
                            navController.navigate(Route.Login) {
                                popUpTo(Route.ZensiGraph) { inclusive = true }
                            }
                        }
                    )
                }

                // --- Demo ---
                composable<Route.Demo> {
                    val viewModel = koinViewModel<DemoViewModel>()
                    DemoScreenRoot(
                        viewModel = viewModel,
                        onBackClick = { navController.navigateUp() }
                    )
                }
            }

            // ================================================================
            // Book Navigation Graph (reference architecture)
            // ================================================================
            navigation<Route.BookGraph>(
                startDestination = Route.BookList
            ) {
                composable<Route.BookList>(
                    exitTransition = {
                        slideOutHorizontally(
                            targetOffsetX = { -it },
                            animationSpec = tween(300, easing = FastOutSlowInEasing)
                        )
                    },
                    popEnterTransition = {
                        slideInHorizontally(
                            initialOffsetX = { -it },
                            animationSpec = tween(300, easing = FastOutSlowInEasing)
                        )
                    }
                ) {
                    val viewModel = koinViewModel<BookListViewModel>()
                    val selectedBookViewModel = it.sharedKoinViewModel<SelectedBookViewModel>(navController)

                    LaunchedEffect(true) {
                        selectedBookViewModel.onSelectBook(null)
                    }

                    BookListScreenRoot(
                        viewModel = viewModel,
                        onBookClick = { book ->
                            selectedBookViewModel.onSelectBook(book)
                            navController.navigate(Route.BookDetail(book.id))
                        }
                    )
                }

                composable<Route.BookDetail>(
                    enterTransition = { slideInHorizontally { it } },
                    exitTransition = { slideOutHorizontally { it } }
                ) {
                    val selectedBookViewModel = it.sharedKoinViewModel<SelectedBookViewModel>(navController)
                    val viewModel = koinViewModel<BookDetailViewModel>()
                    val selectedBook by selectedBookViewModel.selectedBook.collectAsStateWithLifecycle()

                    LaunchedEffect(selectedBook) {
                        selectedBook?.let { book ->
                            viewModel.onAction(BookDetailAction.OnSelectedBookChange(book))
                        }
                    }

                    BookDetailScreenRoot(
                        viewModel = viewModel,
                        onBackClick = { navController.navigateUp() }
                    )
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
