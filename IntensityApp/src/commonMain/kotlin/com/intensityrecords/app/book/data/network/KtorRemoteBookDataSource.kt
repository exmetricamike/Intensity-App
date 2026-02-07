package com.intensityrecord.book.data.network

import com.intensityrecord.book.data.dto.BookWorkDto
import com.intensityrecord.book.data.dto.SearchResponseDto
import com.intensityrecord.core.data.safeCall
import com.intensityrecord.core.domain.DataError
import com.intensityrecord.core.domain.Result
import com.intensityrecord.logDebug
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter


private const val BASE_URL = "https://openlibrary.org"

/*
* Performs the calls to the api
* */
class KtorRemoteBookDataSource(
    private val httpClient: HttpClient
) : RemoteBookDataSource {


    /***
     * Search book api call
     */
    override suspend fun searchBooks(
        query: String,
        resultLimit: Int?
    ): Result<SearchResponseDto, DataError.Remote> {
        //logDebug("*** KtorRemoteBookDataSource", "Searching for books: $query")
        return safeCall {
            // this returns the responseDto
            httpClient.get(urlString = "$BASE_URL/search.json")
            {
                parameter("q", query)
                parameter("limit", resultLimit)
                parameter("language", "eng")
                parameter(
                    "fields",
                    "key, title, language, cover_i, author_name, author_key, first_publish_year, cover_edition_key, ratings_average, ratings_count , number_of_pages_median, edition_count "
                )
            }
        }
    }

    override suspend fun getBookDetails(bookWorkId: String): Result<BookWorkDto, DataError.Remote> {
        return safeCall<BookWorkDto> {
            httpClient.get(
                urlString = "$BASE_URL/works/$bookWorkId.json"
            )
        }
    }

}


