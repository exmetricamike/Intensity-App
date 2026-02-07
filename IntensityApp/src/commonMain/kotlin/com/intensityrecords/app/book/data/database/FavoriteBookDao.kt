package com.intensityrecord.book.data.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

/*
* Data Access Objects are interface in Kotlin that define how to interact with a database
* They can include a variety of query methods.
*/
@Dao
interface FavoriteBookDao {

    @Upsert //annotation to tell room that the entity can be inserted or updated
    suspend fun upsert(book: BookEntity)

    @Query("SELECT * FROM BookEntity")
    fun getFavoriteBooks(): Flow<List<BookEntity>>  //with Flow we can observe changes in the database

    @Query("SELECT * FROM BookEntity WHERE id = :id")
    suspend fun getFavoriteBook(id: String): BookEntity?

    @Query("DELETE FROM BookEntity WHERE id = :id")
    suspend fun deleteFavoriteBook(id: String)
}