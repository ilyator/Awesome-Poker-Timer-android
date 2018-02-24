package com.ily.pakertymer.database.model

import android.annotation.SuppressLint
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by ily on 20.10.2016.
 */
@SuppressLint("ParcelCreator")
@Parcelize
@Entity
data class Tournament(var name: String? = null,
                      var isActive: Boolean = false,
                      var isRunning: Boolean = false,
                      var currentLevelTime: Long = 0,
                      @PrimaryKey(autoGenerate = true) var id : Long = 0,
                      @Ignore var currentLevel: Level? = null)
    : Parcelable
