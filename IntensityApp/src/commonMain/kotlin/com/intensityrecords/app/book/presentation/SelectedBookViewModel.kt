package com.intensityrecord.book.presentation

import androidx.lifecycle.ViewModel
import com.intensityrecord.book.domain.Book
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * This is a shared viewmodel and has the purpose of sharing data between screens
 */
class SelectedBookViewModel: ViewModel() {

    private val _selectedBook = MutableStateFlow<Book?>(null)
    val selectedBook = _selectedBook.asStateFlow()

    fun onSelectBook(book: Book?) {
        _selectedBook.value = book
    }
}