package com.ily.pakertymer.util

import android.os.CountDownTimer

import com.ily.pakertymer.events.TickEvent
import com.ily.pakertymer.events.TimerFinishedEvent

import org.greenrobot.eventbus.EventBus

/**
* Created by ily on 18/04/2017.
*/

class TournamentTimer(millisInFuture: Long, countDownInterval: Long) : CountDownTimer(millisInFuture, countDownInterval) {

    override fun onTick(millisUntilFinished: Long) {
        EventBus.getDefault().post(TickEvent(millisUntilFinished))
    }

    override fun onFinish() {
        EventBus.getDefault().post(TimerFinishedEvent())
    }
}
