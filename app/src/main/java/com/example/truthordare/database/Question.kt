package com.example.truthordare.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "question")
data class Question (

    @PrimaryKey
    @ColumnInfo(name = "name")
    var question: String,

    @ColumnInfo(name = "type")
    var type: Boolean = false
)
