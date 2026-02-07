package com.intensityrecord.book.data.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


/**
 * This abstract class defines the roomDatabase for the app.
 */
@Database(
    entities = [BookEntity::class], // defines the entities for this database, in other words the tables
    version = 1
)
@TypeConverters(
    StringListTypeConverter::class
)
@ConstructedBy(BookDatabaseConstructor::class)
abstract class FavoriteBookDatabase: RoomDatabase() {
    abstract val favoriteBookDao: FavoriteBookDao

    companion object {
        const val DB_NAME = "book.db"
    }
}