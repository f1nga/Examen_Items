package com.example.examenitems.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.examenitems.models.Item

@Database(
    entities = [Item::class],
    version = 1,
    exportSchema = true
)

abstract class ItemDatabase: RoomDatabase(){
    abstract fun itemDao(): ItemDAO

    companion object {

        @Volatile
        private var INSTANCE: ItemDatabase? = null

        fun getDatabase(context: Context): ItemDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            if (INSTANCE == null) {
                synchronized(this) {
                    // Pass the database to the INSTANCE
                    INSTANCE = buildDatabase(context)
                }
            }
            // Return database.
            return INSTANCE!!
        }

        private fun buildDatabase(context: Context): ItemDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                ItemDatabase::class.java,
                "item_database"
            ).createFromAsset("database/examen_items.db")
                .build()
        }
    }
}