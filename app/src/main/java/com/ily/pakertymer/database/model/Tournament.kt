package com.ily.pakertymer.database.model

import android.annotation.SuppressLint
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by ily on 20.10.2016.
 */
@SuppressLint("ParcelCreator")
@Parcelize
@Entity
data class Tournament(@PrimaryKey var id: Int,
                      var name: String? = null,
                      var isActive: Boolean = false,
                      var isRunning: Boolean = false,
                      var currentLevelTime: Long = 0)
    : Parcelable
