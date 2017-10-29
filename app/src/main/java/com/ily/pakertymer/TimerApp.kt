package com.ily.pakertymer

import android.app.Application
import android.preference.PreferenceManager
import com.crashlytics.android.Crashlytics
import com.ily.pakertymer.Constants.IS_FIRST_LAUNCH
import com.ily.pakertymer.util.TournamentsUtil
import io.fabric.sdk.android.Fabric
import io.realm.Realm
import io.realm.RealmConfiguration

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

        initDefaultTournaments()
    }

    private fun initDefaultTournaments() {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        if (sharedPreferences.getBoolean(IS_FIRST_LAUNCH, true)) {
            TournamentsUtil.createDefaultTournaments()
            sharedPreferences.edit().putBoolean(IS_FIRST_LAUNCH, false).apply()
        }
    }

}
