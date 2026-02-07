package com.intensityrecord.book.data.mappers

import com.intensityrecord.book.data.database.BookEntity
import com.intensityrecord.book.data.dto.SearchedBookDto
import com.intensityrecord.book.domain.Book

fun SearchedBookDto.toBook(): Book {
    return Book(
        id = id.substringAfterLast('/') ,
        title = title,
        imageUrl = if (coverKey != null) {
            "https://covers.openlibrary.org/b/olid/${coverKey}-L.jpg"
        } else {
            "https://covers.openlibrary.org/b/olid/${coverAlternativeKey}-L.jpg"
        },
        authors = authorName?: emptyList(),
        description = null,
        languages = languages?: emptyList(),
        firstPublishYear = firstPublishYear.toString(),
        averageRating = ratingsAverage,
        ratingCount = ratingsCount,
        numPages = numPagesMedian,
        numEditions = 2,
    )
}

// this map a book to an entity that can go in the database
fun Book.toBookEntity(): BookEntity {
    return BookEntity(
        id = id,
        title = title,
        description = description,
        imageUrl = imageUrl,
        languages = languages,
        authors = authors,
        firstPublishYear = firstPublishYear,
        ratingsAverage = averageRating,
        ratingsCount = ratingCount,
        numPagesMedian = numPages,
        numEditions = numEditions
    )
}

fun BookEntity.toBook(): Book {
    return Book(
        id = id,
        title = title,
        description = description,
        imageUrl = imageUrl,
        languages = languages,
        authors = authors,
        firstPublishYear = firstPublishYear,
        averageRating = ratingsAverage,
        ratingCount = ratingsCount,
        numPages = numPagesMedian,
        numEditions = numEditions
    )
}