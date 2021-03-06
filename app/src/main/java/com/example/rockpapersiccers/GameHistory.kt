package com.example.rockpapersiccers

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "game_history")
data class GameHistory (

    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Long? = null,

    @ColumnInfo(name = "win")
    var win: Int,

    @ColumnInfo(name = "draw")
    var draw: Int,

    @ColumnInfo(name = "loss")
    var loss: Int,

    @ColumnInfo(name = "cpumove")
    var cpuMove: Int,

    @ColumnInfo(name = "playermove")
    var playerMove: Int,

    @ColumnInfo(name = "gameresult")
    var gameResult: String,

    @ColumnInfo(name = "date")
    var date: Date?


):Parcelable