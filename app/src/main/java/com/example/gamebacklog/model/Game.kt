package com.example.gamebacklog.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "table_game")
data class Game(

    @ColumnInfo
    var title: String,

    @ColumnInfo
    var platform: String,

    @ColumnInfo
    var releaseDate: Date,

    @PrimaryKey(autoGenerate = true)

    var id: Long? = null
): Parcelable