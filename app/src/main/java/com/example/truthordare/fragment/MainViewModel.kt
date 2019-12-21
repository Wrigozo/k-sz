package com.example.truthordare.fragment

import androidx.lifecycle.ViewModel
import com.example.truthordare.database.*
import kotlinx.coroutines.*

class MainViewModel (val playertable: PlayerDatabaseDao,val questiontable: QuestionDatabaseDao): ViewModel() {
    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    var players=playertable.getAllPlayers()

    private suspend fun deleteAll(){
        withContext(Dispatchers.IO){
            questiontable.clear()
        }
    }

    fun deleteDatabase(){
        uiScope.launch {
            deleteAll()
        }
    }

    private suspend fun insertAll(dares:List<Question>){
        withContext(Dispatchers.IO){
            questiontable.insertAll(dares)
        }
    }

    fun buildDatabase(dares:List<Question>){
        uiScope.launch {
            insertAll(dares)
        }
    }


    private suspend fun insert(name:String){
        withContext(Dispatchers.IO){
            val player=Player()
            player.name=name
            playertable.insert(player)
        }
    }

    fun addDatabase(name:String){
        uiScope.launch {
            insert(name)
        }
    }

    private suspend fun getlist(bool:Boolean){
        withContext(Dispatchers.IO){
            questiontable.get(bool)
        }
    }

    fun getDatabase2(bool:Boolean){
        uiScope.launch {
            getlist(bool)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }


    var truthes =questiontable.get(true)

    var dares =questiontable.get(false)
}
