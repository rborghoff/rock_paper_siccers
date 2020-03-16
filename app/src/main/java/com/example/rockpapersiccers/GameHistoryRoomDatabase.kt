package com.example.rockpapersiccers

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [GameHistory::class], version = 2,exportSchema = false)
abstract class GameHistoryRoomDatabase: RoomDatabase() {
    abstract fun historyDao(): HistoryDao

    companion object{
        private const val DATABASE_NAME = "GAME_HISTORY_DATABASE"

        @Volatile
        private var gameHistoryRoomDatabaseInstance: GameHistoryRoomDatabase? = null

        fun getDatabase(context: Context): GameHistoryRoomDatabase?{
            if (gameHistoryRoomDatabaseInstance == null){
                synchronized(GameHistoryRoomDatabase::class.java){
                    if (gameHistoryRoomDatabaseInstance== null){
                        gameHistoryRoomDatabaseInstance=
                            Room.databaseBuilder(context.applicationContext,GameHistoryRoomDatabase::class.java,
                                DATABASE_NAME).build()
                    }
                }
            }
            return gameHistoryRoomDatabaseInstance
        }
    }
}