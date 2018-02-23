package com.ily.pakertymer

import android.app.Application
import android.preference.PreferenceManager
import com.crashlytics.android.Crashlytics
import com.ily.pakertymer.Constants.IS_FIRST_LAUNCH
import com.ily.pakertymer.database.PTDatabase
import com.ily.pakertymer.database.model.Level
import com.ily.pakertymer.database.model.Tournament
import io.fabric.sdk.android.Fabric
import io.realm.Realm
import io.realm.RealmConfiguration
import org.jetbrains.anko.doAsync

/**
 * Created by ily on 21.10.2016.
 */

class TimerApp : Application() {

    override fun onCreate() {
        super.onCreate()
        //Crashlytics init
        Fabric.with(this, Crashlytics())

        //Realm init
        Realm.init(this)
        val config = RealmConfiguration.Builder().build()
        Realm.setDefaultConfiguration(config)

        database = PTDatabase.create(this)
        instance = this

        initDefaultTournaments()
    }

    private fun initDefaultTournaments() {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        if (sharedPreferences.getBoolean(IS_FIRST_LAUNCH, true)) {
            doAsync {
                database.tournamentDao.insert(Tournament(0, "Test"), Tournament(1, "Test1"))
                database.levelDao.insert(Level(0, 50, 10, 20, 0, 0),
                        Level(1, 40, 10, 20, 0, 0),
                        Level(2, 20, 280, 20, 0, 1),
                        Level(3, 40, 290, 246, 0, 1))
                sharedPreferences.edit().putBoolean(IS_FIRST_LAUNCH, false).apply()
            }
        }
    }

    companion object {
        lateinit var instance: TimerApp
            private set

        lateinit var database: PTDatabase
    }

}
