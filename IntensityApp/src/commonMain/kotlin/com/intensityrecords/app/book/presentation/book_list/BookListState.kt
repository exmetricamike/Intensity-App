package com.intensityrecord.book.presentation.book_list

import com.intensityrecord.book.domain.Book
import com.intensityrecord.core.presentation.UiText

/*This file contains everything that belongs to the BookList Screen*/
/*
* MVI architecture: Model view intent is a pattern that specify how the presentation layer is organized, how we
* structure the presentation layer
*
* MVI: we have a view, a view model, Intent.
* Intent is a sealed interface that contains all those UI actions the user could do on a screen that we could send to the
* viewmodel (click on a button, doing a search etc.)
*
* */


data class BookListState (
    // boundles all the things that can be changed as a result of a user interaction,
    // So this contains all the state of the view list
    val searchQuery: String = "Kotlin",
    val searchResults: List<Book> = emptyList(),
    val favoriteBooks: List<Book> = emptyList(),
    val isLoading: Boolean = true,
    val selectedTabIndex: Int = 0,
    val errorMessage: UiText? = null
)
