package com.ily.pakertymer.model

import android.os.Parcel
import android.os.Parcelable

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by ily on 20.10.2016.
 */

open class Level : RealmObject, Parcelable {

    @PrimaryKey
    var id: Int = 0
    var duration: Long = 0
    var smallBlind: Int = 0
    var bigBlind: Int = 0
    var ante: Int = 0


    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(this.id)
        dest.writeLong(this.duration)
        dest.writeInt(this.smallBlind)
        dest.writeInt(this.bigBlind)
        dest.writeInt(this.ante)
    }

    constructor() {}

    protected constructor(`in`: Parcel) {
        this.id = `in`.readInt()
        this.duration = `in`.readLong()
        this.smallBlind = `in`.readInt()
        this.bigBlind = `in`.readInt()
        this.ante = `in`.readInt()
    }

    companion object {

        val CREATOR: Parcelable.Creator<Level> = object : Parcelable.Creator<Level> {
            override fun createFromParcel(source: Parcel): Level {
                return Level(source)
            }

            override fun newArray(size: Int): Array<Level?> {
                return arrayOfNulls(size)
            }
        }
    }
}
