package com.ily.pakertymer.database.model

import android.annotation.SuppressLint
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by ily on 20.10.2016.
 */
@SuppressLint("ParcelCreator")
@Parcelize
@Entity(foreignKeys =
[ForeignKey(entity = Tournament::class,
        parentColumns = ["id"],
        childColumns = ["tournamentId"],
        onDelete = ForeignKey.CASCADE)])
data class Level(var duration: Long = 0,
                 var smallBlind: Int = 0,
                 var bigBlind: Int = 0,
                 var ante: Int = 0,
                 var tournamentId: Long,
                 @PrimaryKey(autoGenerate = true) var id : Long = 0)
    : Parcelable