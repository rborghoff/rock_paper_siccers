package com.example.rockpapersiccers

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface HistoryDao {

    @Query("SELECT * from game_history")
    suspend fun getAllGames(): List<GameHistory>

    @Query("DELETE FROM game_history")
    suspend fun deleteAllGames()

    @Insert
    suspend fun insertGame(gameHistory: GameHistory)

    @Query("SELECT count (win) FROM game_history")
    suspend fun getWins(): Int

    @Query("SELECT count (draw) FROM game_history ")
    suspend fun getDraws(): Int

    @Query("SELECT count (loss) FROM game_history")
    suspend fun getLosses(): Int

}