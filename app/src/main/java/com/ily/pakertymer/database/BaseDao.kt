package com.ily.pakertymer.database

import android.arch.persistence.room.*

/**
 * Created by ily on 22.02.2018.
 */
@Dao
interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(items: T)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(vararg levels: T)

    @Update
    fun update(vararg levels: T)

    @Delete
    fun delete(vararg levels: T)

}