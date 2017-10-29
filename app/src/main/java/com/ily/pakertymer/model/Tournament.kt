package com.ily.pakertymer.model

import android.os.Parcel
import android.os.Parcelable
import io.realm.RealmList
import io.realm.RealmObject

/**
 * Created by ily on 20.10.2016.
 */

open class Tournament : RealmObject, Parcelable {

    var index: Int = 0
    var name: String? = null
    var levels: RealmList<Level>? = null
    var isActive: Boolean = false
    var isRunning: Boolean = false
    var currentLevel: Level? = null
    var currentLevelTime: Long = 0


    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(this.index)
        dest.writeString(this.name)
        dest.writeList(this.levels)
        dest.writeByte(if (this.isActive) 1.toByte() else 0.toByte())
        dest.writeByte(if (this.isRunning) 1.toByte() else 0.toByte())
        dest.writeParcelable(this.currentLevel, flags)
        dest.writeLong(this.currentLevelTime)
    }

    constructor()

    protected constructor(`in`: Parcel) {
        this.index = `in`.readInt()
        this.name = `in`.readString()
        this.levels = RealmList()
        `in`.readList(this.levels, Level::class.java.classLoader)
        this.isActive = `in`.readByte().toInt() != 0
        this.isRunning = `in`.readByte().toInt() != 0
        this.currentLevel = `in`.readParcelable(Level::class.java.classLoader)
        this.currentLevelTime = `in`.readLong()
    }

    companion object {

        val CREATOR: Parcelable.Creator<Tournament> = object : Parcelable.Creator<Tournament> {
            override fun createFromParcel(source: Parcel): Tournament {
                return Tournament(source)
            }

            override fun newArray(size: Int): Array<Tournament?> {
                return arrayOfNulls(size)
            }
        }
    }



}
