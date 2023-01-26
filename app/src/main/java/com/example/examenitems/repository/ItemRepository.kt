package com.example.examenitems.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.examenitems.database.ItemDatabase
import com.example.examenitems.models.Item
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ItemRepository {
    companion object {

        private var itemDatabase: ItemDatabase? = null

        var items: LiveData<List<Item>>? = null

        fun initializeDB(context: Context): ItemDatabase {
            return ItemDatabase.getDatabase(context)
        }

        fun getItems(context: Context): LiveData<List<Item>>? {
            itemDatabase = initializeDB(context)

            items = itemDatabase!!.itemDao().getItems()

            return items
        }

        fun insertItem(context: Context, item: Item) {
            itemDatabase = initializeDB(context)

            CoroutineScope(Dispatchers.IO).launch {
                itemDatabase!!.itemDao().addItem(item)
            }
        }

        fun deleteItem(context: Context, item: Item) {
            itemDatabase = initializeDB(context)

            CoroutineScope(Dispatchers.IO).launch {
                itemDatabase!!.itemDao().deleteItem(item)
            }
        }

        fun updateData(context: Context, currentItem: Item, newItem: Item) {
            itemDatabase = initializeDB(context)

            CoroutineScope(Dispatchers.IO).launch {
                itemDatabase!!.itemDao().updateData(currentItem, newItem)
            }
        }

    }
}