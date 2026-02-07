package com.intensityrecord

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.intensityrecord.auth.presentation.LoginScreen
import com.intensityrecord.auth.presentation.LoginState
import com.intensityrecord.book.domain.Book
import com.intensityrecord.book.presentation.book_list.BookListScreen
import com.intensityrecord.book.presentation.book_list.BookListState
import com.intensityrecord.book.presentation.book_list.components.BookSearchBar


@Preview
@Composable
private fun LoginPreview() {

    Surface(modifier = Modifier.fillMaxSize())
    {
        LoginScreen(
            state = LoginState(),
            onEvent = { }
        )
    }

}


@Preview
@Composable
private fun BookSearchBarPreview() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        BookSearchBar(
            searchQuery = "",
            onSearchQueryChange = {},
            onImeSearch = { },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
        )
    }
}


private val books = (1..10).map {
    Book(
        id = it.toString(),
        title = "Book $it",
        imageUrl = "https://test.com",
        authors = listOf("Author"),
        description = "Description",
        languages = emptyList(),
        firstPublishYear = null,
        averageRating = 4.6758,
        ratingCount = 2,
        numPages = 100,
        numEditions = 23
    )
}


@Preview
@Composable
private fun BookListScreenPreview() {
    BookListScreen(
        state = BookListState(searchResults = books),
        onAction = {}
    )
}