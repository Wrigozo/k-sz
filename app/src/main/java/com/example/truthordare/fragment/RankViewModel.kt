package com.example.truthordare.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.truthordare.database.Player
import com.example.truthordare.database.PlayerDatabaseDao
import kotlinx.coroutines.*

class RankViewModel (val database: PlayerDatabaseDao) : ViewModel() {
    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    var players=database.getAllPlayers()
    private var _player= MutableLiveData<Player>()
    val player: LiveData<Player>
        get()=_player

    init {
        _player.value= Player(0,"test",1)
    }

    val stringPlayer= Transformations.map(player){
            player -> player.name
    }

    fun addDatabase(name:String){
        uiScope.launch {
            insert(name)
        }
    }

    private suspend fun insert(name:String){
        withContext(Dispatchers.IO){
            val player= Player()
            player.name=name
            database.insert(player)
        }
    }

    fun deletePlayers(){
        uiScope.launch {
            onCleared()
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}

