package com.example.truthordare.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface PlayerDatabaseDao {

    @Insert
    fun insert(player: Player)

    @Update
    fun update(player: Player)

    @Query("SELECT * from player WHERE name = :key")
    fun get(key: String): Player?

    @Query("DELETE FROM player")
    fun clear()

    @Query("SELECT * FROM player ORDER BY point DESC")
    fun getAllPlayers(): LiveData<List<Player>>
}