package com.intensityrecord.book.domain

import com.intensityrecord.core.domain.DataError
import com.intensityrecord.core.domain.Result
import kotlinx.coroutines.flow.Flow

// The pattern would be violated if we accessed the repository defined in the data layer here in the domain layer,
// So there is this interface that is created and used in the domain layer to access the data layer.
// the DefaultBookRepository in data layer implements this interface
interface BookRepository {
    suspend fun searchBooks(query: String): Result<List<Book>, DataError.Remote> // this only uses api calls
    suspend fun getBookDescription(bookId: String): Result<String?, DataError> //this might load from database

    // Functions to retrieve books from Database
    fun getFavoriteBooks(): Flow<List<Book>>
    fun isBookFavorite(id: String): Flow<Boolean>
    suspend fun markAsFavorite(book: Book) : Result<Unit, DataError.Local> //EmptyResult<DataError.Local>
    suspend fun deleteFromFavorites(id: String)
}