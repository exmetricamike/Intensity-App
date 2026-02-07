package com.intensityrecord.book.data.network

import com.intensityrecord.book.data.dto.BookWorkDto
import com.intensityrecord.book.data.dto.SearchResponseDto
import com.intensityrecord.core.domain.DataError
import com.intensityrecord.core.domain.Result

/**
 * Interface for the remote data source. It creates an abstraction so the implementation of the network library can be changed
 */
interface RemoteBookDataSource {
    suspend fun searchBooks(
        query:String,
        resultLimit: Int? = null
    ): Result<SearchResponseDto, DataError.Remote>

    suspend fun getBookDetails(bookWorkId: String): Result<BookWorkDto, DataError.Remote>
}