package com.intensityrecord.book.presentation.book_list

import com.intensityrecord.book.domain.Book

/*
* This is a sealed interface encoding all the action the user could perform on this screen
*
* BookListAction is a sealed interface, meaning all its implementations are known at compile-time
*  and must be in the same file.
* Each action (OnSearchQueryChange, OnBookClick, OnTabSelected) is a distinct, immutable event the user can trigger.
* These actions carry data — e.g., the search query string, the book clicked, the selected tab index - so they are naturally represented as data classes.
* data class is used in sealed interface hierarchies like BookListAction to clearly model all possible user actions, each with their own data payload.
* Immutability: Actions are immutable and hold fixed data.
**/

sealed interface BookListAction {
    // All these actions have an impact on the state
    // when the user changes the search query
    data class OnSearchQueryChange(val query:String): BookListAction
    // when we tap on a book
    data class OnBookClick(val book: Book): BookListAction
    // when we switch the tab
    data class OnTabSelected(val index: Int): BookListAction
}