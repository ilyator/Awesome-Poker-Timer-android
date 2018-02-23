package com.ily.pakertymer.database

import android.arch.persistence.room.*

/**
 * Created by ily on 22.02.2018.
 */
@Dao
interface BaseDao<in T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(items: T) : Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg items: T) : Array<Long>

    @Update
    fun update(vararg items: T)

    @Delete
    fun delete(vararg items: T)

}