package com.ily.pakertymer.database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.ily.pakertymer.database.model.Tournament
import com.ily.pakertymer.database.model.TournamentWithLevels

/**
 * Created by ily on 22.02.2018.
 */
@Dao
interface TournamentDao : BaseDao<Tournament> {

    @Query("SELECT * FROM Tournament")
    fun getTournamentsAsync(): LiveData<List<Tournament>>

    @Query("SELECT * FROM Tournament")
    fun getTournaments(): List<Tournament>

    @Transaction
    @Query("SELECT * FROM Tournament")
    fun getTournamentsWithLevelsAsync(): LiveData<List<TournamentWithLevels>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(items: List<Tournament>): Array<Long>

}