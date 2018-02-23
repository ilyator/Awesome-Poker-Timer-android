package com.ily.pakertymer.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import com.ily.pakertymer.database.model.Level
import com.ily.pakertymer.database.model.Tournament


/**
 * Created by ily on 15-Jan-18.
 */
@Database(entities = [Level::class, Tournament::class], version = 1)
@TypeConverters(Converters::class)
abstract class PTDatabase : RoomDatabase() {

    abstract val tournamentDao: TournamentDao
    abstract val levelDao: LevelDao

    companion object {
        private const val DB_NAME = "PTDatabase.db"
        fun create(context: Context) = Room.databaseBuilder(context, PTDatabase::class.java, DB_NAME)
                .build()
    }
}