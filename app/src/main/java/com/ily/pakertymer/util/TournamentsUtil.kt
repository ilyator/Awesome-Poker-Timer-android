package com.ily.pakertymer.util

import android.content.Context
import com.ily.pakertymer.R
import com.ily.pakertymer.TimerApp
import com.ily.pakertymer.database.model.Level
import com.ily.pakertymer.database.model.Tournament
import com.ily.pakertymer.database.model.TournamentWithLevels
import org.jetbrains.anko.doAsync

/**
 * Created by ily on 21.10.2016.
 */

object TournamentsUtil {

    fun createDefaultTournaments() {
        doAsync {
            TimerApp.database.tournamentDao
                    .insert((0..9).map {
                        Tournament("Default" + it.toString(), false, false, 1000 * 60)
                    })
                    .forEach { id ->
                        (0..5).map { Level(1000 * 60, 10, 20, 0, id) }
                                .forEach { TimerApp.database.levelDao.insert(it) }
                    }
        }
    }

    fun getBlindsInfo(context: Context, tournament: TournamentWithLevels): String {
        val levels = tournament.levels
        val startBlind = levels[0].smallBlind
        val endBlind = levels[0].bigBlind
        return String.format(context.getString(R.string.blinds_d_d), startBlind, endBlind)
    }

    fun getLevelsInfo(context: Context, tournament: TournamentWithLevels): String {
        val levels = tournament.levels
        return String.format(context.getString(R.string.d_levels_d_minutes), levels.size, tournament.levels[0].duration / 1000 / 60)
    }

}
