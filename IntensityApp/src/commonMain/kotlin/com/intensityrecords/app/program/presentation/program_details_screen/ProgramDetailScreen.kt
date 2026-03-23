package com.intensityrecords.app.program.presentation.program_details_screen

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.intensityrecord.core.presentation.PrimaryAccent
import com.intensityrecords.app.core.presentation.LocalAppDimens
import com.intensityrecords.app.core.presentation.components.MuxVideoPlayer
import com.intensityrecords.app.home.presentation.home_screen.component.VideoPlayerAutoPlayPlaceholder
import com.intensityrecords.app.program.presentation.program_details_screen.component.ProgramHeroSection
import com.intensityrecords.app.program.presentation.program_details_screen.component.ProgramSessionCard
import intensityrecordapp.intensityapp.generated.resources.Res
import intensityrecordapp.intensityapp.generated.resources.montserrat_regular
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.Font
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ProgramDetailScreenRoot(
    navController: NavController,
    isWideScreen: Boolean,
    collectionId: Int,
    onBackClick: () -> Unit,
    viewModel: ProgramDetailScreenViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(collectionId) {
        viewModel.loadCollection(collectionId)
    }

    ProgramDetailScreen(
        state = state,
        isWideScreen = isWideScreen,
        onAction = { action ->
            if (action is ProgramDetailAction.OnBackClick) {
                onBackClick()
            }
        }
    )
}

@Composable
fun ProgramDetailScreen(
    state: ProgramDetailState,
    isWideScreen: Boolean,
    onAction: (ProgramDetailAction) -> Unit,
) {
    val dimens = LocalAppDimens.current

    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    val pagerState = rememberPagerState(pageCount = { state.collection?.videos?.size ?: 0 })
    val scope = rememberCoroutineScope()

    val cardWidth = if (isWideScreen) 280.dp else 170.dp

    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    var cardSize by remember { mutableStateOf(IntSize.Zero) }

    var selectedVideoPlaybackId by remember { mutableStateOf<String?>(null) }

    val closeFocusRequester = remember { FocusRequester() }
    val closeInteractionSource = remember { MutableInteractionSource() }
    val isCloseFocused by closeInteractionSource.collectIsFocusedAsState()

    val closeScale by animateFloatAsState(if (isCloseFocused) 1.2f else 1f)
    val closeStrokeWidth by animateDpAsState(if (isCloseFocused) 3.dp else 0.dp)

    LaunchedEffect(isFocused) {
        if (isFocused) {
            val size = cardSize.toSize()
            val extraBottomSpace = size.height * 0.2f
            val expandedRect = Rect(
                left = 0f,
                top = 0f,
                right = size.width,
                bottom = size.height + extraBottomSpace
            )
            bringIntoViewRequester.bringIntoView(expandedRect)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(if (isWideScreen) 0.dp else 35.dp))

            Box(modifier = Modifier.padding(horizontal = dimens.horizontalContentPadding)) {
                ProgramHeroSection(item = state.collection, isWideScreen = isWideScreen)
            }

            Spacer(modifier = Modifier.height(35.dp))

            if (!isWideScreen) {
                Text(
                    text = "This month sessions",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(Res.font.montserrat_regular)),
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(horizontal = dimens.horizontalContentPadding)
                )

                Spacer(modifier = Modifier.height(if (isWideScreen) 0.dp else 34.dp))
            }

            HorizontalPager(
                state = pagerState,
                pageSize = PageSize.Fixed(cardWidth),
                contentPadding = PaddingValues(horizontal = if (isWideScreen) 180.dp else 120.dp),
                pageSpacing = if (isWideScreen) 16.dp else 20.dp,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth().height(if (isWideScreen) 280.dp else 200.dp)
            ) { pageIndex ->
                val pageOffset =
                    (pagerState.currentPage - pageIndex) + pagerState.currentPageOffsetFraction
                val scaleFactor = 1f - (0.15f * kotlin.math.abs(pageOffset)).coerceIn(0f, 0.3f)
                val alphaFactor = 1f - (0.3f * kotlin.math.abs(pageOffset)).coerceIn(0f, 0.5f)

                Box(
                    modifier = Modifier.graphicsLayer {
                        scaleX = scaleFactor
                        scaleY = scaleFactor
                        alpha = alphaFactor
                    }.fillMaxHeight()
                        .onSizeChanged { cardSize = it }
                        .bringIntoViewRequester(bringIntoViewRequester)
                        .onFocusChanged { focusState ->
                            if (focusState.hasFocus) {
                                scope.launch {
                                    pagerState.animateScrollToPage(pageIndex)
                                }
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    val session = state.collection?.videos?.get(pageIndex)

                    ProgramSessionCard(
                        session = session,
                        isWideScreen = isWideScreen,
                        onClick = {
                            scope.launch { pagerState.animateScrollToPage(pageIndex) }
                            selectedVideoPlaybackId = "n2KvjXdPt02d5uPGwdqZo18g2ZGYjeiHwsvqzCIxIAFw"
                        },
                        dimens = dimens
                    )
                }
            }

            if (state.collection?.videos?.isNotEmpty() == true) {
                Spacer(modifier = Modifier.height(if (isWideScreen) 0.dp else 12.dp))
                ProgramScrollIndicator(
                    count = state.collection.videos.size,
                    activeIndex = pagerState.currentPage,
                    modifier = Modifier.padding(vertical = 24.dp)
                )
            }

            if (selectedVideoPlaybackId != null) {
                Dialog(
                    onDismissRequest = { selectedVideoPlaybackId = null },
                    properties = DialogProperties(usePlatformDefaultWidth = false)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize().background(Color.Black)
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize().background(Color.Black),
                            contentAlignment = Alignment.Center
                        ) {
                            MuxVideoPlayer(
                                modifier = Modifier.fillMaxWidth().aspectRatio(16f / 9f),
                                playbackId = selectedVideoPlaybackId!!
                            )
                        }

                        Box(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(24.dp)
                                .size(56.dp)
                                .shadow(
                                    elevation = if (isCloseFocused) 6.dp else 0.dp,
                                    shape = CircleShape,
                                    spotColor = PrimaryAccent
                                )
                                .focusRequester(closeFocusRequester)
                                .background(
                                    color = if (isCloseFocused) Color.Black else Color.Black.copy(alpha = 0.5f),
                                    shape = CircleShape
                                )
                                .border(
                                    width = closeStrokeWidth,
                                    color = PrimaryAccent,
                                    shape = CircleShape
                                )
                                .clickable(
                                    interactionSource = closeInteractionSource,
                                    indication = null
                                ) { selectedVideoPlaybackId = null },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close Video",
                                tint = if (isCloseFocused) PrimaryAccent else Color.White,
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun ProgramScrollIndicator(
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
            val width by animateDpAsState(if (isSelected) 18.dp else 6.dp)
            val color by animateColorAsState(
                if (isSelected) PrimaryAccent else Color.White.copy(alpha = 0.3f)
            )
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
