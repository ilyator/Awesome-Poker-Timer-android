package com.ily.pakertymer.service

import android.app.Service
import android.content.Intent
import android.content.SharedPreferences
import android.os.IBinder
import android.preference.PreferenceManager
import android.support.v4.app.NotificationCompat
import com.ily.pakertymer.Constants.SP_KEY_TOURNAMENT
import com.ily.pakertymer.R
import com.ily.pakertymer.database.model.TournamentWithLevels
import com.ily.pakertymer.events.TickEvent
import com.ily.pakertymer.events.TimerFinishedEvent
import com.ily.pakertymer.util.TournamentTimer
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Alina on 4/20/2017.
 */

class TimerService : Service() {

    private var timer: TournamentTimer? = null
    private var tournamentWithLevels: TournamentWithLevels? = null
    private var sharedPreferences: SharedPreferences? = null
    private var timeFormat: SimpleDateFormat = SimpleDateFormat("mm:ss", Locale.US)
    private var timerCalendar: Calendar? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        tournamentWithLevels = intent?.getParcelableExtra(SP_KEY_TOURNAMENT)
        timer = TournamentTimer(tournamentWithLevels
                ?.tournament?.currentLevel?.duration ?: 60000,
                1000)
        timer?.start()
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this)
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
        timerCalendar?.timeInMillis = timeLeftInMillis
        //TODO: use notification channel
        val mBuilder = NotificationCompat.Builder(applicationContext)
                .setAutoCancel(false)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Timer is running")
                .setOngoing(true)
                .setContentText(timeFormat.format(timerCalendar!!.time))
        startForeground(1337, mBuilder.build())
    }

    @Subscribe
    fun onTimerTickEvent(tickEvent: TickEvent) {
        if (tournamentWithLevels != null) {
            updateNotification(tickEvent.millisUntilFinished)
            tournamentWithLevels?.tournament?.currentLevelTime = tickEvent.millisUntilFinished
        }
    }

    @Subscribe
    fun onTimerFinishedEvent(timerFinishedEvent: TimerFinishedEvent) {
        stopForeground(true)
        stopSelf()
    }

}
