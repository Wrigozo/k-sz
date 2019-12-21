package com.example.truthordare.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface QuestionDatabaseDao {

    @Insert
    fun insert(question: Question)

    @Insert
    fun insertAll(questions: List<Question>)

    @Update
    fun update(question: Question)

    @Query("SELECT name from question WHERE type = :key")
    fun get(key: Boolean): LiveData<List<String>>?

    @Query("DELETE FROM question")
    fun clear()

    @Query("SELECT * FROM question ORDER BY name DESC")
    fun getAllQuestion(): LiveData<List<Question>>
}