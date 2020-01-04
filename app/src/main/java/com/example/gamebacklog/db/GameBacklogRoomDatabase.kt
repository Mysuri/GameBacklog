package com.example.notepad.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.gamebacklog.database.Converters
import com.example.gamebacklog.model.Game
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZoneId
import java.util.*


@Database(entities = [Game::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class GameBacklogRoomDatabase: RoomDatabase() {

    abstract fun GameDao(): GameDao

    companion object {
        private const val DATABASE_NAME = "GAMEBACKLOG_DATABASE"

        @Volatile
        private var INSTANCE: GameBacklogRoomDatabase? = null

        fun getDatabase(context: Context): GameBacklogRoomDatabase? {
            if (INSTANCE == null) {
                synchronized(GameBacklogRoomDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            GameBacklogRoomDatabase::class.java, DATABASE_NAME
                        )
                            .fallbackToDestructiveMigration()
                            .build()
                    }
                }
            }
            return INSTANCE
        }
    }

}