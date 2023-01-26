package com.example.examenitems

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.examenitems.database.ItemDatabase
import com.example.examenitems.models.Item
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }
}