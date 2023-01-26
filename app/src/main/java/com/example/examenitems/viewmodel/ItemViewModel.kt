package com.example.examenitems.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.examenitems.models.Item
import com.example.examenitems.repository.ItemRepository

class ItemViewModel: ViewModel() {

    var items: LiveData<List<Item>>? = null

    private var _item : Item? = null

    fun getItems(context: Context): LiveData<List<Item>>? {
        items =  ItemRepository.getItems(context)

        return items
    }

    fun insertItem(context: Context, item: Item) {
        ItemRepository.insertItem(context, item)
    }

    fun deleteItem(context: Context, item: Item) {
        ItemRepository.deleteItem(context, item)
    }

    fun updateData(context: Context, currentItem: Item, newItem: Item) {
        ItemRepository.updateData(context, currentItem, newItem)
    }

    fun setItem(item: Item) {
        _item = item
    }

    fun getItem(): Item? {
        return _item
    }
}