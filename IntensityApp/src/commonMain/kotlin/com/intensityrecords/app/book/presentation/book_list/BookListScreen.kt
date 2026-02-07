package com.intensityrecord.book.presentation.book_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.room.util.TableInfo
import com.intensityrecord.book.domain.Book
import com.intensityrecord.book.presentation.book_list.components.BookList
import com.intensityrecord.book.presentation.book_list.components.BookSearchBar
import com.intensityrecord.core.presentation.DarkBlue
import com.intensityrecord.core.presentation.DesertWhite
import com.intensityrecord.core.presentation.SandYellow
import com.intensityrecord.logDebug
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import com.intensityrecord.resources.*

// BookListScreenRoot is used to isolate the UI in the BookListScreen to make it independent on navigation and ViewModel
// so it can be tested etc

/*
* typical pattern in Jetpack Compose using state collection, a ViewModel, and unidirectional data flow. Let’s break it down, especially focusing on collectAsStateWithLifecycle.
*
* collectAsStateWithLifecycle() is an extension function that collects a Flow<T> safely in a Compose-aware,
* lifecycle-aware way, converting it into a State<T> that Compose can reactively observe.

Without lifecycle awareness:
If you just use collectAsState() inside a composable, the flow is collected whenever the composable is recomposed —
* even if the screen is in the background, paused, or not visible. That can cause unnecessary work, memory leaks, or even crashes.

With collectAsStateWithLifecycle():
It ensures the flow is only collected when the lifecycle is at least STARTED — for example, when the screen is visible and interactive. This makes it safe and efficient for UI layers.

*
* */

/**
 * This is a root composable that is responsible to collect the state
 */
@Composable
fun BookListScreenRoot(
    viewModel: BookListViewModel = koinViewModel(),
    onBookClick: (Book) -> Unit, //lambda that has impact on navigation, so that the navHost can use the composable actions to trigger navigations
    //modifier: Modifier = Modifier
) {

    // Step 1: Collect lifecycle-aware state from the ViewModel
    val state by viewModel.state.collectAsStateWithLifecycle()
    // Step 2: Call the subcomposable without passing the ViewModel via Koin.
    // Pass state + actions down to the actual screen UI
    BookListScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is BookListAction.OnBookClick -> onBookClick(action.book) // this is so we can call the nav controller later
                else -> Unit
            }
            // No matter what the action is, forward to viewModel
            viewModel.onAction(action)
        })
}


/**
 * By not relying on the viewModel reference, this screen becomes easy to test and to preview
 */
@Composable
fun BookListScreen(
    state: BookListState,
    onAction: (BookListAction) -> Unit,
    modifier: Modifier = Modifier
) {
    // Get the keyboard controller to hide the keyboard
    val keyboardController = LocalSoftwareKeyboardController.current

    val pagerState = rememberPagerState { 2 } // the number of pages in the horizontal pager
    val searchResultListState = rememberLazyListState()
    val favoriteBooksListState = rememberLazyListState()

    /*
    When it runs: Every time state.searchResults changes (i.e., new search results are loaded).
    What it does: Smoothly scrolls the LazyList (or similar) to the top (item at index 0).
    * */

    /*LaunchedEffect(key1, key2, ...) {
    suspend function calls (side effects)
    }
    // LaunchedEffect runs the block once when the composable first enters the composition.
    // It restarts the block whenever any key changes.
    // It automatically cancels the previous coroutine when restarted.
    // It is lifecycle-aware, so it won't run if the composable is not part of the UI tree anymore.
    */

    LaunchedEffect(state.searchResults) {
        // LaunchedEffect is a side-effect API in Jetpack Compose used to launch coroutines
        // that run when a key value changes or when the composable enters the composition.
        searchResultListState.animateScrollToItem(0)
    }

    /*
    * When it runs: Every time the selected tab index (state.selectedTabIndex) changes.
      What it does: Animates the HorizontalPager (or similar) to scroll to the newly selected page.
    **/
    LaunchedEffect(state.selectedTabIndex) {
        // when state.selectedTabIndex changes this block will be called
        pagerState.animateScrollToPage(state.selectedTabIndex)
    }

    /*
    * When it runs: Whenever the current page of the pager changes.
    What it does: If the user has finished scrolling (not in progress), it triggers an action to update the selected tab to match the current pager page.
    **/
    LaunchedEffect(pagerState.currentPage) {
        onAction(BookListAction.OnTabSelected(pagerState.currentPage))
    }



    Column(
        modifier = Modifier.fillMaxSize().background(DarkBlue).statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BookSearchBar(
            searchQuery = state.searchQuery,
            /*onSearchQueryChange = {s -> testCallback(s)},*/
            onSearchQueryChange = {
                // This calls the action on the viewModel, the view model receives it and updates the state
                // when the state changes, it will trigger an update in the UI since it is a StateFlow
                logDebug("*** BookListScreen", "onSearchQueryChange: $it")
                onAction(BookListAction.OnSearchQueryChange(it))
            },
            /* the code above is shorthand for
            onSearchQueryChange = { query ->
                onAction(BookListAction.OnSearchQueryChange(query))
            }*/
            onImeSearch = { keyboardController?.hide() },
            modifier = Modifier.widthIn(max = 400.dp) //width of the textfield will never be larger than 400dp
                .fillMaxWidth().padding(16.dp) // still try to fill width and add padding
        )
        Surface(
            Modifier.weight(1f).fillMaxWidth(),
            color = DesertWhite,
            shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                TabRow(selectedTabIndex = state.selectedTabIndex,
                    modifier = Modifier.padding(vertical = 12.dp).widthIn(max = 700.dp).fillMaxWidth(),
                    containerColor = DesertWhite,
                    contentColor = SandYellow,
                    indicator = { tabPositions ->
                        TabRowDefaults.SecondaryIndicator(
                            color = SandYellow,
                            modifier = Modifier.tabIndicatorOffset(
                                tabPositions[state.selectedTabIndex]
                            )
                        )
                    }
                ) {
                    Tab(
                        selected = state.selectedTabIndex == 0,
                        onClick = { onAction(BookListAction.OnTabSelected(0)) },
                        modifier = modifier.weight(1f),
                        //enabled = TODO(),
                        selectedContentColor = SandYellow,
                        unselectedContentColor = Color.Black.copy(alpha = 0.5f)
                        //selectedContentColor = TODO(),
                        //unselectedContentColor = TODO(),
                        //interactionSource = TODO(),

                    ) {
                        Text(
                            text = stringResource(Res.string.search_results),
                            modifier = Modifier.padding(vertical = 12.dp)
                        )
                    }

                    Tab(
                        selected = state.selectedTabIndex == 1,
                        onClick = { onAction(BookListAction.OnTabSelected(1)) },
                        modifier = modifier.weight(1f),
                        //enabled = TODO(),
                        selectedContentColor = SandYellow,
                        unselectedContentColor = Color.Black.copy(alpha = 0.5f)
                        //selectedContentColor = TODO(),
                        //unselectedContentColor = TODO(),
                        //interactionSource = TODO(),

                    ) {
                        Text(
                            text = stringResource(Res.string.favorites),
                            modifier = Modifier.padding(vertical = 12.dp)
                        )
                    }
                }


                Spacer(modifier = Modifier.height(4.dp))

                // this is the container that lets you swipe horizontally
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxWidth().weight(1f),
//                contentPadding = TODO(),
//                pageSize = TODO(),
//                beyondViewportPageCount = TODO(),
//                pageSpacing = TODO(),
//                verticalAlignment = TODO(),
//                flingBehavior = TODO(),
//                userScrollEnabled = TODO(),
//                reverseLayout = TODO(),
//                key = TODO(),
//                pageNestedScrollConnection = TODO(),
//                snapPosition = TODO(),

                ) {
                    //page content
                    // the lambda make pageIndex available that tells you in which page you are
                    // depending on the pageIndex, draw the corresponding composables
                        pageIndex ->
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        when (pageIndex) {
                            0 -> {
                                if (state.isLoading) {
                                    CircularProgressIndicator()
                                } else {
                                    when {
                                        state.errorMessage != null -> {
                                            Text(
                                                text = state.errorMessage.asString(),
                                                textAlign = TextAlign.Center,
                                                style = MaterialTheme.typography.headlineSmall,
                                                color = MaterialTheme.colorScheme.error
                                            )
                                        }

                                        state.searchResults.isEmpty() -> {
                                            Text(
                                                text = stringResource(Res.string.no_search_results),
                                                textAlign = TextAlign.Center,
                                                style = MaterialTheme.typography.headlineSmall,
                                                color = MaterialTheme.colorScheme.error
                                            )
                                        }

                                        else -> {
                                            // we found at least one book
                                            BookList(
                                                books = state.searchResults,
                                                onBookClick = {
                                                    onAction(BookListAction.OnBookClick(it))
                                                },
                                                modifier = Modifier.fillMaxSize(),
                                                scrollState = searchResultListState
                                            )
                                        }
                                    }
                                }
                            }

                            1 -> {
                                if (state.favoriteBooks.isEmpty()) {
                                    Text(
                                        text = stringResource(Res.string.no_favorite_books),
                                        textAlign = TextAlign.Center,
                                        style = MaterialTheme.typography.headlineSmall,
                                        color = MaterialTheme.colorScheme.error
                                    )
                                } else {
                                    BookList(
                                        books = state.favoriteBooks,
                                        onBookClick = {
                                            onAction(BookListAction.OnBookClick(it))
                                        },
                                        modifier = Modifier.fillMaxSize(),
                                        scrollState = favoriteBooksListState
                                    )
                                }

                            }
                        }
                    }
                }
            }
        }
    }
}