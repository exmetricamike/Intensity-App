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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.intensityrecord.core.presentation.DarkGradient
import com.intensityrecord.core.presentation.FitnessAppTheme
import com.intensityrecords.app.core.data.AuthState
import com.intensityrecords.app.core.data.SessionProvider
import com.intensityrecords.app.core.presentation.CompactDimens
import com.intensityrecords.app.core.presentation.ExpandedDimens
import com.intensityrecords.app.core.presentation.LanguageViewModel
import com.intensityrecords.app.core.presentation.LocalAppDimens
import com.intensityrecords.app.core.presentation.components.AppHeader
import com.intensityrecords.app.core.presentation.components.CustomBottomBar
import com.intensityrecords.app.core.presentation.utils.LocalAppLocale
import com.intensityrecords.app.core.presentation.utils.currentDeviceConfiguration
import com.intensityrecords.app.core.presentation.utils.rememberDataStore
import com.intensityrecords.app.home.presentation.home_screen.HomeScreenRoot
import com.intensityrecords.app.home.presentation.video_detail_screen.VideoDetailScreen
import com.intensityrecords.app.live.presentation.live_screen.LiveScreenRoot
import com.intensityrecords.app.live.presentation.timetable_screen.TimeTableScreen
import com.intensityrecords.app.login.presentation.login_screen.LoginScreenRoot
import com.intensityrecords.app.login.presentation.login_screen.LoginScreenViewModel
import com.intensityrecords.app.mobility.presentation.mobility_screen.MobilityScreenRoot
import com.intensityrecords.app.steptrip.presentation.SelectedStepTripViewModel
import com.intensityrecords.app.steptrip.presentation.step_trip_detail_screen.StepTripDetailAction
import com.intensityrecords.app.steptrip.presentation.step_trip_detail_screen.StepTripDetailScreenRoot
import com.intensityrecords.app.steptrip.presentation.step_trip_detail_screen.StepTripsDetailScreenViewModel
import com.intensityrecords.app.steptrip.presentation.steptrip.StepTripScreenRoot
import com.intensityrecords.app.workouts.presentation.SelectedWorkOutViewModel
import com.intensityrecords.app.workouts.presentation.workouts_details_screen.WorkOutsDetailAction
import com.intensityrecords.app.workouts.presentation.workouts_details_screen.WorkOutsDetailScreenViewModel
import com.intensityrecords.app.workouts.presentation.workouts_details_screen.WorkoutDetailScreenRoot
import com.intensityrecords.app.workouts.presentation.workouts_screen.WorkoutScreenRoot
import com.intensityrecords.app.program.presentation.program_screen.ProgramScreenRoot
import com.intensityrecords.app.program.presentation.program_details_screen.ProgramDetailScreenRoot
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.intensityrecords.app.core.presentation.Inter
import intensityrecordapp.intensityapp.generated.resources.Res
import intensityrecordapp.intensityapp.generated.resources.home
import intensityrecordapp.intensityapp.generated.resources.ic_intensity_logo
import intensityrecordapp.intensityapp.generated.resources.live
import intensityrecordapp.intensityapp.generated.resources.mobility
import intensityrecordapp.intensityapp.generated.resources.workouts
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject
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
            val dataStore = rememberDataStore()
            val isWideScreen = currentDeviceConfiguration().isWideScreen
            val animationDuration = if (isWideScreen) 500 else 300
            val dimens = if (!isWideScreen) CompactDimens else ExpandedDimens

//            val viewModel = viewModel {
//                LanguageViewModel(dataStore)
//            }
            val viewModel: LanguageViewModel = koinViewModel()


            val languageCode by viewModel.languageCode.collectAsStateWithLifecycle()

            val sessionProvider: SessionProvider = koinInject()
            val token by sessionProvider.authToken.collectAsStateWithLifecycle(initialValue = null)
            val id by sessionProvider.authId.collectAsStateWithLifecycle(initialValue = null)


            val startDestination = if (token == null) Route.Login else Route.Home

            val authState by sessionProvider.authState.collectAsStateWithLifecycle(AuthState.Loading)

            CompositionLocalProvider(LocalAppLocale provides languageCode) {

                CompositionLocalProvider(LocalAppDimens provides dimens) {


                    when (authState) {

                        AuthState.Loading -> {
                            SplashScreen()
                        }

                        AuthState.LoggedOut -> {
                            MainApp(
                                startDestination = Route.Login,
                                isWideScreen = isWideScreen,
                                sessionProvider = sessionProvider
                            )
                        }

                        is AuthState.LoggedIn -> {
                            MainApp(
                                startDestination = Route.Home,
                                isWideScreen = isWideScreen,
                                sessionProvider = sessionProvider
                            )
                        }
                    }

//                    val navController = rememberNavController()
//                    val currentBackStackEntry by navController.currentBackStackEntryAsState()
//                    val currentDestination = currentBackStackEntry?.destination
//
//                    val currentTab = when {
//                        currentDestination?.hasRoute<Route.Home>() == true -> stringResource(Res.string.home)
//
//                        currentDestination?.hasRoute<Route.Live>() == true ||
//                                currentDestination?.hasRoute<Route.TimeTable>() == true -> stringResource(
//                            Res.string.live
//                        )
//
//                        currentDestination?.hasRoute<Route.WorkOuts>() == true ||
//                                currentDestination?.hasRoute<Route.WorkOutsDetailsScreen>() == true -> stringResource(
//                            Res.string.workouts
//                        )
//
//                        currentDestination?.hasRoute<Route.Mobility>() == true -> stringResource(Res.string.mobility)
//
//                        currentDestination?.hasRoute<Route.StepTrip>() == true ||
//                                currentDestination?.hasRoute<Route.StepTripDetailScreen>() == true -> stringResource(
//                            Res.string.home
//                        )
//
//                        else -> stringResource(Res.string.home)
//                    }
//
//                    val showBars = currentDestination?.hasRoute<Route.Login>() == false
//
//                    Scaffold(
//                        containerColor = Color.Transparent,
//                        modifier = Modifier.windowInsetsPadding(WindowInsets.safeDrawing),
//                        topBar = {
//                            AppHeader(isWideScreen = isWideScreen)
//                        },
//                        bottomBar = {
//                            if (showBars) {
//                                Box(
//                                    modifier = Modifier
//                                        .fillMaxWidth()
//                                        .navigationBarsPadding()
//                                        .padding(bottom = 10.dp),
//                                    contentAlignment = Alignment.BottomCenter
//                                ) {
//                                    CustomBottomBar(
//                                        isWideScreen = isWideScreen,
//                                        currentTab = currentTab,
//                                        navController = navController,
//                                        viewModel = viewModel
//                                    )
//                                }
//                            }
//                        }
//                    ) { innerPadding ->
//                        NavHost(
//                            navController = navController,
//                            startDestination = startDestination,
//                            modifier = Modifier.padding(
//                                top = innerPadding.calculateTopPadding(),
//                                bottom = innerPadding.calculateBottomPadding()
//                            )
//                        ) {
//
//                            composable<Route.Login>(
//                                enterTransition = { fadeIn(tween(animationDuration)) },
//                                exitTransition = { fadeOut(tween(animationDuration)) }
//                            ) {
//
//                                LoginScreenRoot(
//                                    onLoginSuccess = {
//                                        navController.navigate(Route.Home) {
//                                            popUpTo(Route.Login) { inclusive = true }
//                                        }
//                                    }
//                                )
//                            }
//
//                            composable<Route.Home>(
//                                enterTransition = { fadeIn(animationSpec = tween(animationDuration)) },
//                                exitTransition = { fadeOut(animationSpec = tween(animationDuration)) },
//                                popEnterTransition = {
//                                    fadeIn(
//                                        animationSpec = tween(
//                                            animationDuration
//                                        )
//                                    )
//                                },
//                                popExitTransition = {
//                                    fadeOut(
//                                        animationSpec = tween(
//                                            animationDuration
//                                        )
//                                    )
//                                }
//                            ) {
//                                HomeScreenRoot(navController, isWideScreen)
//                            }
//
//                            composable<Route.VideoDetail>(
//                                enterTransition = { fadeIn(animationSpec = tween(animationDuration)) },
//                                exitTransition = { fadeOut(animationSpec = tween(animationDuration)) },
//                                popEnterTransition = {
//                                    fadeIn(
//                                        animationSpec = tween(
//                                            animationDuration
//                                        )
//                                    )
//                                },
//                                popExitTransition = {
//                                    fadeOut(
//                                        animationSpec = tween(
//                                            animationDuration
//                                        )
//                                    )
//                                }
//                            ) {
//                                VideoDetailScreen(navController, isWideScreen)
//                            }
//
//                            composable<Route.Live>(
//                                enterTransition = { fadeIn(animationSpec = tween(animationDuration)) },
//                                exitTransition = { fadeOut(animationSpec = tween(animationDuration)) },
//                                popEnterTransition = {
//                                    fadeIn(
//                                        animationSpec = tween(
//                                            animationDuration
//                                        )
//                                    )
//                                },
//                                popExitTransition = {
//                                    fadeOut(
//                                        animationSpec = tween(
//                                            animationDuration
//                                        )
//                                    )
//                                }
//                            ) {
//                                LiveScreenRoot(navController, isWideScreen)
//                            }
//
//                            composable<Route.TimeTable>(
//                                enterTransition = { fadeIn(animationSpec = tween(animationDuration)) },
//                                exitTransition = { fadeOut(animationSpec = tween(animationDuration)) },
//                                popEnterTransition = {
//                                    fadeIn(
//                                        animationSpec = tween(
//                                            animationDuration
//                                        )
//                                    )
//                                },
//                                popExitTransition = {
//                                    fadeOut(
//                                        animationSpec = tween(
//                                            animationDuration
//                                        )
//                                    )
//                                }
//                            ) {
//                                TimeTableScreen(
//                                    navController = navController,
//                                    isWideScreen = isWideScreen
//                                )
//                            }
//
//                            composable<Route.WorkOuts>(
//                                enterTransition = { fadeIn(animationSpec = tween(animationDuration)) },
//                                exitTransition = { fadeOut(animationSpec = tween(animationDuration)) },
//                                popEnterTransition = {
//                                    fadeIn(
//                                        animationSpec = tween(
//                                            animationDuration
//                                        )
//                                    )
//                                },
//                                popExitTransition = {
//                                    fadeOut(
//                                        animationSpec = tween(
//                                            animationDuration
//                                        )
//                                    )
//                                }
//                            ) {
////                                WorkoutScreenRoot(navController, isWideScreen)
//
//                                val selectedWorkOutViewModel =
//                                    it.sharedKoinViewModel<SelectedWorkOutViewModel>(navController)
//
//                                LaunchedEffect(true) {
//                                    selectedWorkOutViewModel.onSelectWorkOut(null)
//                                }
//
//                                WorkoutScreenRoot(
//                                    onWorkOutClick = { workOut ->
//                                        selectedWorkOutViewModel.onSelectWorkOut(workOut)
//                                        navController.navigate(Route.WorkOutsDetailsScreen(workOut.id))
//                                    },
//                                    isWideScreen = isWideScreen,
//                                    navController = navController
//                                )
//
//                            }
//
////                            // Arguments are now handled via Type-Safe object
////                            composable<Route.WorkOutsDetailsScreen> { backStackEntry ->
////                                val args = backStackEntry.toRoute<Route.WorkOutsDetailsScreen>()
////
////                                // Find the actual object using the ID from the Route
//////                                val selectedItem = workoutCategories.find { it.title == args.id }
////
//////                                if (selectedItem != null) {
////                                    WorkoutDetailScreenRoot(
////                                        navController = navController,
////                                        workoutId = args.id,
////                                        isWideScreen = isWideScreen
////                                    )
//////                                }
////                            }
//
////                            composable<Route.WorkOutsDetailsScreen>(
////                                enterTransition = { fadeIn(animationSpec = tween(animationDuration)) },
////                                exitTransition = { fadeOut(animationSpec = tween(animationDuration)) },
////                                popEnterTransition = {
////                                    fadeIn(
////                                        animationSpec = tween(
////                                            animationDuration
////                                        )
////                                    )
////                                },
////                                popExitTransition = {
////                                    fadeOut(
////                                        animationSpec = tween(
////                                            animationDuration
////                                        )
////                                    )
////                                }
////                            ) {
////
////                                val selectedBookViewModel =
////                                    it.sharedKoinViewModel<SelectedWorkOutViewModel>(navController)
////                                val viewModel = koinViewModel<WorkOutsDetailScreenViewModel>()
////                                val selectedBook by selectedBookViewModel.selectedWorkOut.collectAsStateWithLifecycle()
////
////                                println("Selected ${selectedBook}")
////
//////                                selectedBook?.let { workout ->
//////
//////                                    LaunchedEffect(workout) {
//////                                        viewModel.onAction(
//////                                            WorkOutsDetailAction.OnSelectedWorkOutChange(workout)
//////                                        )
//////                                    }
//////
//////                                    WorkoutDetailScreenRoot(
//////                                        onBackClick = { navController.navigateUp() },
//////                                        isWideScreen = isWideScreen,
//////                                        navController = navController,
//////                                    )
//////                                }
////
////                                if (selectedBook == null) {
////                                    Box(
////                                        modifier = Modifier.fillMaxSize(),
////                                        contentAlignment = Alignment.Center
////                                    ) {
////                                        Text("Loading workout...")
////                                    }
////                                } else {
////
////                                    LaunchedEffect(selectedBook) {
////                                        viewModel.onAction(
////                                            WorkOutsDetailAction.OnSelectedWorkOutChange(selectedBook!!)
////                                        )
////                                    }
////
////                                    WorkoutDetailScreenRoot(
////                                        onBackClick = { navController.navigateUp() },
////                                        isWideScreen = isWideScreen,
////                                        navController = navController,
////                                        selectedBook = selectedBook!!
////                                    )
////                                }
////
////                            }
//                            composable<Route.WorkOutsDetailsScreen>(
//                                enterTransition = { fadeIn(animationSpec = tween(animationDuration)) },
//                                exitTransition = { fadeOut(animationSpec = tween(animationDuration)) },
//                                popEnterTransition = {
//                                    fadeIn(
//                                        animationSpec = tween(
//                                            animationDuration
//                                        )
//                                    )
//                                },
//                                popExitTransition = {
//                                    fadeOut(
//                                        animationSpec = tween(
//                                            animationDuration
//                                        )
//                                    )
//                                }
//                            ) {
//
//                                val selectedBookViewModel =
//                                    it.sharedKoinViewModel<SelectedWorkOutViewModel>(navController)
//                                val viewModel = koinViewModel<WorkOutsDetailScreenViewModel>()
//
//                                val selectedBook by selectedBookViewModel.selectedWorkOut.collectAsStateWithLifecycle()
//
//                                LaunchedEffect(selectedBook) {
//                                    selectedBook?.let { workoutItem ->
//                                        println("Details :- $workoutItem")
//                                        viewModel.onAction(
//                                            WorkOutsDetailAction.OnSelectedWorkOutChange(workoutItem)
//                                        )
//                                    }
//                                }
//
//                                // ADD THIS NULL CHECK: Only show the screen if the whole data item has arrived
//                                if (selectedBook != null) {
//                                    WorkoutDetailScreenRoot(
//                                        onBackClick = {
//                                            navController.navigateUp()
//                                        },
//                                        isWideScreen = isWideScreen,
//                                        navController = navController,
//                                        selectedBook = selectedBook!!
//                                    )
//                                }
//                            }
//
//                            composable<Route.Mobility>(
//                                enterTransition = { fadeIn(animationSpec = tween(animationDuration)) },
//                                exitTransition = { fadeOut(animationSpec = tween(animationDuration)) },
//                                popEnterTransition = {
//                                    fadeIn(
//                                        animationSpec = tween(
//                                            animationDuration
//                                        )
//                                    )
//                                },
//                                popExitTransition = {
//                                    fadeOut(
//                                        animationSpec = tween(
//                                            animationDuration
//                                        )
//                                    )
//                                }
//                            ) {
//                                MobilityScreenRoot(navController, isWideScreen)
//                            }
//
//                            composable<Route.StepTrip>(
//                                enterTransition = { fadeIn(animationSpec = tween(animationDuration)) },
//                                exitTransition = { fadeOut(animationSpec = tween(animationDuration)) },
//                                popEnterTransition = {
//                                    fadeIn(
//                                        animationSpec = tween(
//                                            animationDuration
//                                        )
//                                    )
//                                },
//                                popExitTransition = {
//                                    fadeOut(
//                                        animationSpec = tween(
//                                            animationDuration
//                                        )
//                                    )
//                                }
//                            ) {
////                                StepTripScreenRoot(navController, isWideScreen)
//
//                                val selectedStepTripViewModel =
//                                    it.sharedKoinViewModel<SelectedStepTripViewModel>(navController)
//
//                                LaunchedEffect(true) {
//                                    selectedStepTripViewModel.onSelectStepTrip(null)
//                                }
//
//                                StepTripScreenRoot(
//                                    onStepTripClick = { stepTrip ->
//                                        selectedStepTripViewModel.onSelectStepTrip(stepTrip)
//                                        navController.navigate(Route.StepTripDetailScreen(stepTrip.id))
//                                    },
//                                    isWideScreen = isWideScreen,
//                                    navController = navController
//                                )
//
//
//                            }
//
//                            composable<Route.StepTripDetailScreen>(
//                                enterTransition = { fadeIn(animationSpec = tween(animationDuration)) },
//                                exitTransition = { fadeOut(animationSpec = tween(animationDuration)) },
//                                popEnterTransition = {
//                                    fadeIn(
//                                        animationSpec = tween(
//                                            animationDuration
//                                        )
//                                    )
//                                },
//                                popExitTransition = {
//                                    fadeOut(
//                                        animationSpec = tween(
//                                            animationDuration
//                                        )
//                                    )
//                                }
//                            ) {
//
//                                val selectedStepTripViewModel =
//                                    it.sharedKoinViewModel<SelectedStepTripViewModel>(navController)
//                                val viewModel = koinViewModel<StepTripsDetailScreenViewModel>()
//
//                                val selectedBook by selectedStepTripViewModel.selectedStepTrip.collectAsStateWithLifecycle()
//
//                                LaunchedEffect(selectedBook) {
//                                    selectedBook?.let { workoutItem ->
//                                        println("Details :- $workoutItem")
//                                        viewModel.onAction(
//                                            StepTripDetailAction.OnSelectedWorkOutChange(workoutItem)
//                                        )
//                                    }
//                                }
//
//                                // ADD THIS NULL CHECK: Only show the screen if the whole data item has arrived
//                                if (selectedBook != null) {
//                                    StepTripDetailScreenRoot(
//                                        onBackClick = {
//                                            navController.navigateUp()
//                                        },
//                                        isWideScreen = isWideScreen,
//                                        navController = navController,
//                                        selectedBook = selectedBook!!
//                                    )
//                                }
//
//                            }
////                            composable<Route.StepTripDetailScreen> { backStackEntry ->
////                                val args = backStackEntry.toRoute<Route.StepTripDetailScreen>()
////
////                                val selectedItem = trips.find { it.title == args.id }
////
////                                if (selectedItem != null) {
////                                    StepTripDetailScreenRoot(
////                                        navController = navController,
////                                        stepTripID = args.id,
////                                        isWideScreen = isWideScreen
////                                    )
////                                }
////                            }
//
//                        }
//                    }
                }

            }
        }
    }
}

@Composable
fun SplashScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(Res.drawable.ic_intensity_logo),
                contentDescription = "Intensity logo",
                modifier = Modifier.size(120.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Intensity",
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = Inter
            )
            Spacer(modifier = Modifier.height(32.dp))
            CircularProgressIndicator(
                color = Color.White,
                strokeWidth = 2.dp,
                modifier = Modifier.size(28.dp)
            )
        }
    }
}

@Composable
fun MainApp(
    startDestination: Any,
    isWideScreen: Boolean,
    sessionProvider: SessionProvider
) {

    val viewModel: LanguageViewModel = koinViewModel()
    val animationDuration = if (isWideScreen) 500 else 300
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination
    val scope = rememberCoroutineScope()

    val currentTab = when {
        currentDestination?.hasRoute<Route.Home>() == true -> stringResource(Res.string.home)

        currentDestination?.hasRoute<Route.Live>() == true ||
                currentDestination?.hasRoute<Route.TimeTable>() == true -> stringResource(
            Res.string.live
        )

        currentDestination?.hasRoute<Route.WorkOuts>() == true ||
                currentDestination?.hasRoute<Route.WorkOutsDetailsScreen>() == true -> stringResource(
            Res.string.workouts
        )

        currentDestination?.hasRoute<Route.Mobility>() == true -> stringResource(Res.string.mobility)

        currentDestination?.hasRoute<Route.StepTrip>() == true ||
                currentDestination?.hasRoute<Route.StepTripDetailScreen>() == true -> stringResource(
            Res.string.home
        )

        else -> stringResource(Res.string.home)
    }

    val showBars = currentDestination?.hasRoute<Route.Login>() == false

    val isLoginScreen = currentDestination?.hasRoute<Route.Login>() == true

    Scaffold(
        containerColor = Color.Transparent,
        modifier = Modifier.windowInsetsPadding(WindowInsets.safeDrawing),
        topBar = {
            AppHeader(
                isWideScreen = isWideScreen,
                onLogOut = {
                    scope.launch {
                        sessionProvider.clearSession()
                    }
                },
                isLoginScreen = isLoginScreen
            )
        },
        bottomBar = {
            if (showBars) {
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
                        navController = navController,
                        viewModel = viewModel
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(
                top = innerPadding.calculateTopPadding(),
                bottom = innerPadding.calculateBottomPadding()
            )
        ) {

            composable<Route.Login>(
                enterTransition = { fadeIn(tween(animationDuration)) },
                exitTransition = { fadeOut(tween(animationDuration)) }
            ) {

                LoginScreenRoot(
                    onLoginSuccess = {
                        navController.navigate(Route.Home) {
                            popUpTo(Route.Login) { inclusive = true }
                        }
                    },
                    isWideScreen = isWideScreen
                )
            }

            composable<Route.Home>(
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
                HomeScreenRoot(navController, isWideScreen)
            }

            composable<Route.VideoDetail>(
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

            composable<Route.Live>(
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
                LiveScreenRoot(navController, isWideScreen)
            }

            composable<Route.TimeTable>(
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
                TimeTableScreen(
                    navController = navController,
                    isWideScreen = isWideScreen
                )
            }

            composable<Route.WorkOuts>(
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
                WorkoutScreenRoot(navController, isWideScreen)
            }

            composable<Route.WorkOutsDetailsScreen> { backStackEntry ->

                val args = backStackEntry.toRoute<Route.WorkOutsDetailsScreen>()

                WorkoutDetailScreenRoot(
                    onBackClick = { navController.navigateUp() },
                    isWideScreen = isWideScreen,
                    navController = navController,
                    collectionId = args.id
                )
            }

            composable<Route.Programs>(
                enterTransition = { fadeIn(animationSpec = tween(animationDuration)) },
                exitTransition = { fadeOut(animationSpec = tween(animationDuration)) },
                popEnterTransition = { fadeIn(animationSpec = tween(animationDuration)) },
                popExitTransition = { fadeOut(animationSpec = tween(animationDuration)) }
            ) {
                ProgramScreenRoot(navController, isWideScreen)
            }

            composable<Route.ProgramDetailsScreen> { backStackEntry ->

                val args = backStackEntry.toRoute<Route.ProgramDetailsScreen>()

                ProgramDetailScreenRoot(
                    onBackClick = { navController.navigateUp() },
                    isWideScreen = isWideScreen,
                    navController = navController,
                    collectionId = args.id
                )
            }


            composable<Route.Mobility>(
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
                MobilityScreenRoot(navController, isWideScreen)
            }

            composable<Route.StepTrip>(
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
//                                StepTripScreenRoot(navController, isWideScreen)

                val selectedStepTripViewModel =
                    it.sharedKoinViewModel<SelectedStepTripViewModel>(navController)

                LaunchedEffect(true) {
                    selectedStepTripViewModel.onSelectStepTrip(null)
                }

                StepTripScreenRoot(
                    onStepTripClick = { stepTrip ->
                        selectedStepTripViewModel.onSelectStepTrip(stepTrip)
                        navController.navigate(Route.StepTripDetailScreen(stepTrip.id))
                    },
                    isWideScreen = isWideScreen,
                    navController = navController
                )


            }

            composable<Route.StepTripDetailScreen>(
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

                val selectedStepTripViewModel =
                    it.sharedKoinViewModel<SelectedStepTripViewModel>(navController)
                val viewModel = koinViewModel<StepTripsDetailScreenViewModel>()

                val selectedBook by selectedStepTripViewModel.selectedStepTrip.collectAsStateWithLifecycle()

                LaunchedEffect(selectedBook) {
                    selectedBook?.let { workoutItem ->
                        println("Details :- $workoutItem")
                        viewModel.onAction(
                            StepTripDetailAction.OnSelectedWorkOutChange(workoutItem)
                        )
                    }
                }

                // ADD THIS NULL CHECK: Only show the screen if the whole data item has arrived
                if (selectedBook != null) {
                    StepTripDetailScreenRoot(
                        onBackClick = {
                            navController.navigateUp()
                        },
                        isWideScreen = isWideScreen,
                        navController = navController,
                        selectedBook = selectedBook!!
                    )
                }

            }
//                            composable<Route.StepTripDetailScreen> { backStackEntry ->
//                                val args = backStackEntry.toRoute<Route.StepTripDetailScreen>()
//
//                                val selectedItem = trips.find { it.title == args.id }
//
//                                if (selectedItem != null) {
//                                    StepTripDetailScreenRoot(
//                                        navController = navController,
//                                        stepTripID = args.id,
//                                        isWideScreen = isWideScreen
//                                    )
//                                }
//                            }

        }
    }

}


//@Composable
//private inline fun <reified T : ViewModel> NavBackStackEntry.sharedKoinViewModel(
//    navController: NavController
//): T {
//    val navGraphRoute = destination.parent?.route ?: return koinViewModel<T>()
//    val parentEntry = remember(this) {
//        navController.getBackStackEntry(navGraphRoute)
//    }
//    return koinViewModel(
//        viewModelStoreOwner = parentEntry
//    )
//}

//@Composable
//private inline fun <reified T : ViewModel> NavBackStackEntry.sharedKoinViewModel(
//    navController: NavController
//): T {
//    val navGraphRoute = destination.parent?.route
//    val parentEntry = remember(this) {
//        if (navGraphRoute != null) {
//            navController.getBackStackEntry(navGraphRoute)
//        } else {
//            // Fallback to the root graph if there is no nested graph
//            navController.getBackStackEntry(navController.graph.id)
//        }
//    }
//    return koinViewModel(
//        viewModelStoreOwner = parentEntry
//    )
//}

@Composable
private inline fun <reified T : ViewModel> NavBackStackEntry.sharedKoinViewModel(
    navController: NavController
): T {

    val parentEntry = remember(this) {
        destination.parent?.route?.let {
            navController.getBackStackEntry(it)
        } ?: navController.getBackStackEntry(Route.Home)
    }

    return koinViewModel(
        viewModelStoreOwner = parentEntry
    )
}
