package com.example.truthordare.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import okhttp3.internal.Internal.instance
import java.util.concurrent.Executors

@Database(entities = [Player::class,Question::class], version = 4, exportSchema = false)
abstract class PlayerDatabase : RoomDatabase() {

    abstract val playerDatabaseDao: PlayerDatabaseDao

    abstract fun questionDatabaseDao(): QuestionDatabaseDao

    companion object {

        @Volatile
        private var INSTANCE: PlayerDatabase? = null

        fun getInstance(context: Context): PlayerDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        PlayerDatabase::class.java,
                        "truth_or_dare_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }

        fun buildDatabase(context: Context): PlayerDatabase {
            return Room.databaseBuilder(context, PlayerDatabase::class.java, "truth_or_dare_database")
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        //pre-populate data
                        Executors.newSingleThreadExecutor().execute {
                            INSTANCE?.let {
                                it.questionDatabaseDao().insertAll(DataGenerator.getQuestions())
                            }
                        }
                    }
                })
                .build()
        }
    }
}