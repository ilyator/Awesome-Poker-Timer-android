package com.ily.pakertymer.database.model

import android.annotation.SuppressLint
import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Relation
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

/**
 * Created by ily on 22.02.2018.
 */
@SuppressLint("ParcelCreator")
@Parcelize
@Entity
data class TournamentWithLevels(@Embedded var tournament: Tournament? = null,
                           @Relation(parentColumn = "id", entityColumn = "tournamentId")
                           var levels: List<Level> = ArrayList())
    : Parcelable