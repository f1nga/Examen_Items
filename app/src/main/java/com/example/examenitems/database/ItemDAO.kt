package com.example.examenitems.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.examenitems.models.Item

@Dao
interface ItemDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addItem(item: Item)

    @Query("SELECT * FROM items")
    fun getItems(): LiveData<List<Item>>

    @Delete
    fun deleteItem(item: Item)

    @Transaction
    fun updateData(currentItem: Item, newItem: Item) {
        deleteItem(currentItem)
        addItem(newItem)
    }
}