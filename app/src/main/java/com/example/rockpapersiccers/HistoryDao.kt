package com.example.rockpapersiccers

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface HistoryDao {

    @Query("SELECT * from game_history")
    suspend fun getAllGames(): List<GameHistory>

    @Insert
    suspend fun insertGame(gameHistory: GameHistory)
}