package com.ily.pakertymer.service

import android.app.Service
import android.content.Intent
import android.content.SharedPreferences
import android.os.IBinder
import android.preference.PreferenceManager
import android.support.v4.app.NotificationCompat
import com.ily.pakertymer.R
import com.ily.pakertymer.events.TickEvent
import com.ily.pakertymer.events.TimerFinishedEvent
import com.ily.pakertymer.database.model.Tournament
import com.ily.pakertymer.util.TournamentTimer
import io.realm.Realm
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Alina on 4/20/2017.
 */

class TimerService : Service() {

    private var timer: TournamentTimer? = null
    private var tournament: Tournament? = null
    private var sharedPreferences: SharedPreferences? = null
    private var realm: Realm? = null
    private var timeFormat: SimpleDateFormat? = null
    private var timerCalendar: Calendar? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        val tournamentId: Int = intent?.getIntExtra(KEY_TOURNAMENT, -1) ?: sharedPreferences!!.getInt(KEY_TOURNAMENT, -1)
        realm = Realm.getDefaultInstance()
        if (tournamentId != -1) {
            //tournament = realm!!.where(Tournament::class.java).equalTo("id", tournamentId).findFirst()
        }
        //timer = TournamentTimer(tournament!!.currentLevel!!.duration, 1000)
        timer!!.start()
        EventBus.getDefault().register(this)
        timeFormat = SimpleDateFormat("mm:ss", Locale.US)
        timerCalendar = Calendar.getInstance()
        return Service.START_STICKY_COMPATIBILITY
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }

    private fun updateNotification(timeLeftInMillis: Long) {
        timerCalendar!!.timeInMillis = timeLeftInMillis
        val mBuilder = NotificationCompat.Builder(applicationContext)
                .setAutoCancel(false)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Timer is running")
                .setContentText(timeFormat!!.format(timerCalendar!!.time))
        startForeground(1337, mBuilder.build())
    }

    @Subscribe
    fun onTimerTickEvent(tickEvent: TickEvent) {
        if (tournament != null) {
            updateNotification(tickEvent.millisUntilFinished)
            if (realm != null) {
                realm!!.beginTransaction()
                tournament!!.currentLevelTime = tickEvent.millisUntilFinished
                realm!!.commitTransaction()
            }
        }
    }

    @Subscribe
    fun onTimerFinishedEvent(timerFinishedEvent: TimerFinishedEvent) {
        stopForeground(true)
        stopSelf()
    }

    companion object {

        var KEY_TOURNAMENT = "key_tournament"
    }

}
