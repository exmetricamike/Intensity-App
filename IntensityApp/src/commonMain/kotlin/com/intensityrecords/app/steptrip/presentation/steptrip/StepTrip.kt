package com.intensityrecords.app.steptrip.presentation.steptrip

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.ui.platform.LocalUriHandler
import androidx.navigation.NavController
import com.intensityrecord.core.presentation.DarkGradient
import com.intensityrecord.core.presentation.FitnessAppTheme
import com.intensityrecord.core.presentation.PrimaryAccent
import com.intensityrecords.app.steptrip.domain.StepTripItem
import com.intensityrecords.app.steptrip.presentation.steptrip.component.StepTripCard
import intensityrecordapp.intensityapp.generated.resources.Res
import intensityrecordapp.intensityapp.generated.resources.step_trip
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun StepTripScreenRoot(
    navController: NavController,
    isWideScreen: Boolean,
    viewModel: StepTripScreenViewModel = koinViewModel(),
    onStepTripClick: (StepTripItem) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

//    LaunchedEffect(state.tripData.isEmpty()) {
//        if (state.tripData.isEmpty()) {
//            viewModel.onAction(StepTripAction.LoadWorkouts)
//        }
//    }

    StepTripScreen(
        state = state,
        isWideScreen = isWideScreen,
        onAction = { action ->
//            when (action) {
//                is StepTripAction.OnStripTripClick -> {
//                    navController.navigate(Route.StepTripDetailScreen(id = action.tripData.title))
//                }
//            }
//            viewModel.onAction(action)
            when (action) {
                is StepTripAction.OnStripTripClick -> {
                    onStepTripClick(action.tripData)
                }

                StepTripAction.LoadWorkouts -> {}
            }
            viewModel.onAction(action)
        }
    )

}

@Composable
fun StepTripScreen(
    state: StripTripState,
    isWideScreen: Boolean,
    onAction: (StepTripAction) -> Unit
) {

    val scrollState = rememberScrollState()
    val uriHandler = LocalUriHandler.current

    FitnessAppTheme {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .background(DarkGradient)
        ) {

            val pagerState = rememberPagerState(pageCount = { state.tripData.size })

            if (state.loading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "Loading...",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontSize = if (isWideScreen) 22.sp else 16.sp,
                            textAlign = TextAlign.Center
                        )
                    )
                }
                return@BoxWithConstraints
            }

            if (!state.error.isNullOrEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = state.error,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontSize = if (isWideScreen) 22.sp else 16.sp,
                            textAlign = TextAlign.Center
                        )
                    )
                }
                return@BoxWithConstraints
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = stringResource(Res.string.step_trip).uppercase(),
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(30.dp))

                HorizontalPager(
                    state = pagerState,
                    contentPadding = PaddingValues(horizontal = 80.dp),
                    pageSpacing = 16.dp,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .height(520.dp)
                        .fillMaxWidth()
                ) { page ->

                    val pageOffset =
                        (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction
                    val scaleFactor =
                        1f - (0.15f * kotlin.math.abs(pageOffset)).coerceIn(0f, 0.3f)
                    val alphaFactor =
                        1f - (0.3f * kotlin.math.abs(pageOffset)).coerceIn(0f, 0.5f)

                    Box(
                        modifier = Modifier
                            .graphicsLayer {
                                scaleX = scaleFactor
                                scaleY = scaleFactor
                                alpha = alphaFactor
                            }
                            .fillMaxHeight(),
                        contentAlignment = Alignment.Center
                    ) {
                        StepTripCard(
                            item = state.tripData[page],
                            onClick = { onAction(StepTripAction.OnStripTripClick(tripData = state.tripData[pagerState.currentPage])) },
                            onLetsGoClick = { state.tripData[page].mapsUrl?.let { uriHandler.openUri(it) } }
                        )
                    }
                }

                // --- Navigator Dots ---
                Spacer(modifier = Modifier.height(30.dp))

                Row(
                    Modifier
                        .wrapContentHeight()
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    repeat(pagerState.pageCount) { iteration ->
                        val isSelected = pagerState.currentPage == iteration
                        val color =
                            if (isSelected) PrimaryAccent else Color.Gray.copy(alpha = 0.5f)
                        val width = if (isSelected) 24.dp else 8.dp

                        Box(
                            modifier = Modifier
                                .padding(4.dp)
                                .height(6.dp)
                                .width(width)
                                .clip(CircleShape)
                                .background(color)
                        )
                    }
                }
            }
        }
    }
}
