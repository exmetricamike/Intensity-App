package com.intensityrecord.book.presentation.book_detail

import com.intensityrecord.book.domain.Book

data class BookDetailState(
    val isLoading: Boolean = true,
    val isFavorite: Boolean = false,
    val book: Book? = null
)