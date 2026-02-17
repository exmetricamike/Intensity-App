package com.intensityrecords.app.workouts.presentation.workouts_details_screen

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.intensityrecord.core.presentation.PrimaryAccent
import com.intensityrecord.resources.Res
import com.intensityrecord.resources.montserrat_regular
import com.intensityrecords.app.core.presentation.utils.LocalAppDimens
import com.intensityrecords.app.workouts.presentation.workouts_details_screen.component.HeroSection
import com.intensityrecords.app.workouts.presentation.workouts_details_screen.component.SessionCard
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.Font
import org.koin.compose.viewmodel.koinViewModel
import kotlin.math.absoluteValue

@Composable
fun WorkoutDetailScreenRoot(
    workoutId: String,
    navController: NavController,
    isWideScreen: Boolean,
    viewModel: WorkOutsDetailScreenViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    // Initialize state based on the passed ID
    LaunchedEffect(workoutId) {
        viewModel.initialize(workoutId)
    }

    state.item?.let { workoutItem ->
        WorkoutDetailScreen(
            state = state,
            isWideScreen = isWideScreen,
            onAction = { action ->
                when (action) {
                    WorkOutsDetailAction.OnBackClick -> navController.navigateUp()
                    else -> Unit
                }
                viewModel.onAction(action)
            }
        )
    }
}

@Composable
fun WorkoutDetailScreen(
    state: WorkOutsDetailState,
    isWideScreen: Boolean,
    onAction: (WorkOutsDetailAction) -> Unit,
) {
    val dimens = LocalAppDimens.current

    val pagerState = rememberPagerState(pageCount = { state.sessions.size })
    val scope = rememberCoroutineScope()

    val cardWidth = if (isWideScreen) 280.dp else 170.dp

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black) // or DarkGradient
            .verticalScroll(rememberScrollState())
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally // Centers the indicator
        ) {
            Box(modifier = Modifier.padding(horizontal = dimens.horizontalContentPadding)) {
                state.item?.let {
                    HeroSection(item = it, isWideScreen = isWideScreen)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "This month sessions",
                color = Color.White,
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(Res.font.montserrat_regular)),
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(horizontal = dimens.horizontalContentPadding)
            )

            Spacer(modifier = Modifier.height(20.dp))

            HorizontalPager(
                state = pagerState,
                pageSize = PageSize.Fixed(cardWidth),
                contentPadding = PaddingValues(horizontal = if(isWideScreen) 100.dp else 60.dp),
                pageSpacing = 16.dp,
                modifier = Modifier.fillMaxWidth()
            ) { pageIndex ->
                val session = state.sessions[pageIndex]
                val pageOffset = ((pagerState.currentPage - pageIndex) + pagerState.currentPageOffsetFraction).absoluteValue
                val scale = lerp(start = 0.9f, stop = 1f, fraction = 1f - pageOffset.coerceIn(0f, 1f))

                Box(modifier = Modifier.graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                }) {
                    SessionCard(
                        session = session,
                        isWideScreen = isWideScreen,
                        // Update: Pass the click action to scroll the pager
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(pageIndex)
                            }
                            // Also trigger your MVI action
                            onAction(WorkOutsDetailAction.OnSessionClick(session))
                        },
                        dimens = dimens
                    )
                }
            }

            if (state.sessions.isNotEmpty()) {
                ScrollIndicator(
                    count = state.sessions.size,
                    activeIndex = pagerState.currentPage,
                    modifier = Modifier.padding(vertical = 24.dp)
                )
            }

            Spacer(modifier = Modifier.height(120.dp))

        }
    }
}

@Composable
fun ScrollIndicator(
    count: Int,
    activeIndex: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(count) { index ->
            val isSelected = index == activeIndex
            // Smoothly animate the color and width
            val width by animateDpAsState(if (isSelected) 18.dp else 6.dp)
            val color by animateColorAsState(if (isSelected) PrimaryAccent else Color.White.copy(alpha = 0.3f))

            Box(
                modifier = Modifier
                    .height(6.dp)
                    .width(width)
                    .clip(CircleShape)
                    .background(color)
            )
        }
    }
}


