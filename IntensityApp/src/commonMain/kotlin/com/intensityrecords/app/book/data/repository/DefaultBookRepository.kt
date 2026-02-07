package com.intensityrecord.book.data.repository

import androidx.sqlite.SQLiteException
import coil3.Uri
import com.intensityrecord.book.data.database.FavoriteBookDao
import com.intensityrecord.book.data.mappers.toBook
import com.intensityrecord.book.data.mappers.toBookEntity
import com.intensityrecord.book.data.network.RemoteBookDataSource
import com.intensityrecord.book.domain.Book
import com.intensityrecord.book.domain.BookRepository
import com.intensityrecord.core.domain.DataError
import com.intensityrecord.core.domain.Result
import com.intensityrecord.core.domain.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * The object of a repository is to coordinate the access to multiple data sources
 * they can be Api calls or database calls
 */
class DefaultBookRepository(
    private val remoteBookDataSource: RemoteBookDataSource,
    private val favoriteBookDao: FavoriteBookDao
) : BookRepository {

    /**
     * Triggers an api call to the search endpoint
     */
    override suspend fun searchBooks(query: String): Result<List<Book>, DataError.Remote> {
        return remoteBookDataSource.searchBooks(query).map { dto -> dto.results.map { it.toBook() } }
    }

    /**
     * Triggers an api call to the getBookDescription endpoint
     */
    override suspend fun getBookDescription(bookId: String): Result<String?, DataError> {
        val localResult = favoriteBookDao.getFavoriteBook(bookId)
        return if (localResult != null) {
            Result.Success(localResult.description)
        } else {
            return remoteBookDataSource.getBookDetails(bookId).map { it?.description }
        }
    }

    /**
     * Triggers a database query
     */
    override fun getFavoriteBooks(): Flow<List<Book>> {
        return favoriteBookDao.getFavoriteBooks().map { bookEntities ->
            bookEntities.map { it.toBook() }
        }
    }

    /**
     * Triggers a database query
     */
    override fun isBookFavorite(id: String): Flow<Boolean> {
        return favoriteBookDao.getFavoriteBooks().map { bookEntities -> bookEntities.any { it.id == id } }
    }

    /**
     * Triggers a database query
     */
    override suspend fun markAsFavorite(book: Book): Result<Unit, DataError.Local> {
        return try {
            favoriteBookDao.upsert(book.toBookEntity())
            Result.Success(Unit)
        } catch (e: SQLiteException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    /**
     * Triggers a database query
     */
    override suspend fun deleteFromFavorites(id: String) {
        favoriteBookDao.deleteFavoriteBook(id)
    }
}