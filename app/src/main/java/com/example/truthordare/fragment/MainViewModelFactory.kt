package com.example.truthordare.fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.truthordare.database.PlayerDatabase
import com.example.truthordare.database.PlayerDatabaseDao

import com.example.truthordare.database.QuestionDatabaseDao

class MainViewModelFactory(
    private val dataSource: PlayerDatabaseDao,
    private val dataSource2: QuestionDatabaseDao
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(
                dataSource, dataSource2
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}