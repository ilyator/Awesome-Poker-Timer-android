package com.ily.pakertymer.database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.ily.pakertymer.database.model.Level

/**
 * Created by ily on 22.02.2018.
 */
@Dao
interface LevelDao: BaseDao<Level> {

    @Query("SELECT * FROM Level")
    fun getLevelsAsync(): LiveData<List<Level>>

    @Query("SELECT * FROM Level")
    fun getLevels(): List<Level>

    @Query("SELECT * FROM Level WHERE tournamentId=:tournamentId")
    fun getLevelsForTournamentAsync(tournamentId: Int): LiveData<List<Level>>

    @Query("SELECT * FROM Level WHERE tournamentId=:tournamentId")
    fun getLevelsForTournament(tournamentId: Int): List<Level>

}