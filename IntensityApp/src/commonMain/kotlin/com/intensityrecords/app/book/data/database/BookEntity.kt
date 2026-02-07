package com.intensityrecord.book.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Define the book table in the Db
 */

@Entity // an Entity is essentially a table in Room
data class BookEntity(
    @PrimaryKey(autoGenerate = false) val id: String,
    val title: String,
    val description: String?,
    val imageUrl: String,
    val languages: List<String>, // we can't store list of string in a database so we need a type converter
    val authors: List<String>,
    val firstPublishYear: String?,
    val ratingsAverage: Double?,
    val ratingsCount: Int?,
    val numPagesMedian: Int?,
    val numEditions: Int
)