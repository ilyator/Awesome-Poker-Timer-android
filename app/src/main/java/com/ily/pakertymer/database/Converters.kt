package com.ily.pakertymer.database

import android.arch.persistence.room.TypeConverter
import java.util.*


/**
 * Created by ily on 15-Jan-18.
 */
class Converters {

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return if (value == null) value else Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?) = date?.time
}