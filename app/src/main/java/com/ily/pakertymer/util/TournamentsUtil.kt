package com.ily.pakertymer.util

import android.content.Context

import com.ily.pakertymer.R
import com.ily.pakertymer.model.Level
import com.ily.pakertymer.model.Tournament

import io.realm.Realm
import io.realm.RealmList

/**
 * Created by ily on 21.10.2016.
 */

object TournamentsUtil {

    fun createDefaultTournaments() {
        val realm = Realm.getDefaultInstance()
        realm.beginTransaction()
        for (i in 0..9) {
            val tournament = Tournament()
            val level = Level()
            level.id = i
            level.smallBlind = 10
            level.bigBlind = 20
            level.duration = 1000 * 60
            tournament.index = i
            tournament.levels = RealmList(level)
            tournament.currentLevel = level
            tournament.name = "Default" + i.toString()
            tournament.currentLevelTime = 1000 * 60
            realm.copyToRealm(tournament)
        }
        realm.commitTransaction()
    }

    fun getBlindsInfo(context: Context, tournament: Tournament): String {
        val levels = tournament.levels
        val startBlind = levels!![0]!!.bigBlind
        val endBlind = levels[levels.size - 1]!!.bigBlind
        return String.format(context.getString(R.string.blinds_d_d), startBlind, endBlind)
    }

    fun getLevelsInfo(context: Context, tournament: Tournament): String {
        val levels = tournament.levels
        return String.format(context.getString(R.string.d_levels_d_minutes), levels!!.size, tournament.levels!![0]!!.duration / 1000 / 60)
    }

}
