package com.example.rockpapersiccers

import android.content.Context

class HistoryRepository (context: Context){

    private val historyDao: HistoryDao

    init {
        val database =
            GameHistoryRoomDatabase.getDatabase(context)
        historyDao = database!!.historyDao()
    }

    suspend fun getAllGames(): List<GameHistory> = historyDao.getAllGames()

    suspend fun insertGame(gameHistory: GameHistory) = historyDao.insertGame(gameHistory)
}