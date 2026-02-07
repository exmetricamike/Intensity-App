package com.intensityrecord.book.presentation.book_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.intensityrecord.book.domain.Book
import com.intensityrecord.book.domain.BookRepository
import com.intensityrecord.core.domain.onError
import com.intensityrecord.core.domain.onSuccess
import com.intensityrecord.core.presentation.toUiText
import com.intensityrecord.logDebug
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


// Presentation -> Domain <- Data

class BookListViewModel(
    private val bookRepository: BookRepository // book repository defined in domain layer so we do not access data layer directly
) : ViewModel() {

    private var cachedBooks = emptyList<Book>()
    // this Job takes track of the coroutine job so that the search can be interrupted if there is a new search
    private var searchJob : Job? = null

    private var observeFavoriteJob: Job? = null

    // private mutable version of the state that the view model can mutate
    // Since it is a flow, when the state changes, it will trigger an update in the Ui that will show the new text for example
    // The viewModel can mutate this
    private val _state = MutableStateFlow(BookListState())


    // public immutable version, Emits new UI states
    val state = _state
        .onStart {
            // the ui starts listening to the flow when it does val state by viewModel.state.collectAsStateWithLifecycle()
            // immediately when it starts listening to the flow, this start is called
            if(cachedBooks.isEmpty()) {
                observeSearchQuery()
            }
            observeFavoriteBooks()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L), //while there are active subscribers to the flow, we execute the onStart
            _state.value // inital value
        )




    /**
     * This exposes the way for the Ui to send actions to the viewModel
     * Here the viewModel responds to the actions when something happens in the UI
     */
    fun onAction(action: BookListAction) {
        when (action) {
            is BookListAction.OnBookClick -> {}
            is BookListAction.OnSearchQueryChange -> {
                // update the state in a thread safe manner
                _state.update {
                    // it references the current state. copy it and set the new query coming from the action
                    it.copy(searchQuery = action.query) // updates the state
                }
            }
            is BookListAction.OnTabSelected -> {
                _state.update {
                    // it references the current state. copy it and set the new tab index coming from the action
                    it.copy(selectedTabIndex = action.index)
                }
            }
        }
    }

    private fun observeFavoriteBooks() {
        observeFavoriteJob?.cancel()
        // this flow needs to be save in a coroutine job so that it can be cancelled if the viewModel is destroyed
        observeFavoriteJob = bookRepository
            .getFavoriteBooks()
            .onEach { favoriteBooks ->
                _state.update { it.copy(
                    favoriteBooks = favoriteBooks
                ) }
            }
            .launchIn(viewModelScope)
    }


    private fun observeSearchQuery() {
        state
            .map { it.searchQuery }
            .distinctUntilChanged()
            .debounce(500L)
            .onEach { query ->
                logDebug("**u* ", "observeSearchQuery: $query")
                when {
                    query.isBlank() -> {
                        _state.update {
                            it.copy(
                                errorMessage = null,
                                searchResults = cachedBooks
                            )
                        }
                    }

                    query.length >= 2 -> {
                        searchJob?.cancel()
                        searchJob = searchBooks(query)
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    //  searchBooks is a suspend function, so we need to call it from a coroutine and we do it in the viewModelScope.launch
    private fun searchBooks(query: String) = viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            bookRepository.searchBooks(query)
                .onSuccess { searchResults ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = null,
                            searchResults = searchResults
                        )
                    }
                }
                .onError { error ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = error.toUiText(),
                            searchResults = emptyList()
                        )
                    }
                }
        }
}