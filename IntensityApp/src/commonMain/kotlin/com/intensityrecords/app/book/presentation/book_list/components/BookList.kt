package com.intensityrecord.book.presentation.book_list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.intensityrecord.book.domain.Book
import com.intensityrecord.book.presentation.book_list.BookListAction

/*
* LazyColumn
LazyColumn is a composable used to display a vertically scrolling list where items are only composed when they
*  become visible (hence the term "lazy"). It's like RecyclerView in classic Android.
It's efficient for long lists.
Automatically recycles/composes items as needed
*
*
*  scrollState: LazyListState = rememberLazyListState()
LazyListState allows you to control and observe the scroll state of the LazyColumn.
rememberLazyListState() creates and remembers a scroll state object tied to the composition.
This scrollState allows:
Tracking scroll position
Programmatic scrolling
Preserving scroll position across recompositions
By passing scrollState to LazyColumn via the state parameter, the column uses this object to manage and expose its scroll behavior.
* */

@Composable
fun BookList(
    books: List<Book>, // List of Book data objects to display
    onBookClick: (Book) -> Unit, // lambda callback for when a book is clicked
    modifier: Modifier = Modifier, // Modifier to customize the layout from the parent
    scrollState: LazyListState = rememberLazyListState() // Optional scroll state, defaults to a remembered one
) {

    LazyColumn(
        modifier = modifier,
        state = scrollState,
        //contentPadding = TODO(),
        //reverseLayout = TODO(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        //horizontalAlignment = TODO(),
        //flingBehavior = TODO(),
        //userScrollEnabled = TODO()
    ) {

        items(books,
            key = { it.id }) { book ->
            BookListItem(
                book = book,
                modifier = Modifier.widthIn(max = 700.dp).fillMaxWidth().padding(horizontal = 16.dp),
                onClick = {
                    onBookClick(book)
                }
            )
        }
    }
}