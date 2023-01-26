package com.example.examenitems.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("items")
data class Item(
    @ColumnInfo(name = "nom")
    var nom : String,
    @ColumnInfo(name = "preu")
    var preu : Int,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}
