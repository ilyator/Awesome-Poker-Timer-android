package com.ily.pakertymer.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.ily.pakertymer.R
import com.ily.pakertymer.events.TickEvent
import com.ily.pakertymer.events.TimerFinishedEvent
import com.ily.pakertymer.model.Tournament
import com.ily.pakertymer.service.TimerService
import com.ily.pakertymer.view.CircleTimerView
import io.realm.Realm
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by ily on 20.10.2016.
 */

class TimerFragment : Fragment() {

    @BindView(R.id.iv_timer)
    lateinit var ivTimer: CircleTimerView
    @BindView(R.id.tv_timer)
    lateinit var tvTimer: TextView

    private var realm: Realm? = Realm.getDefaultInstance()
    private var timeFormat: SimpleDateFormat? = null
    private var timerCalendar: Calendar? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val tournament = realm!!.where(Tournament::class.java).equalTo("isActive", true).findFirst()

        val root = inflater!!.inflate(R.layout.fragment_timer, container, false)
        ButterKnife.bind(this, root)
        if (tournament != null) {
            ivTimer.setLevelFullTime(tournament.currentLevel!!.duration)
            ivTimer.setLevelCurrentTime(tournament.currentLevel!!.duration)
        }

        return root
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        timeFormat = SimpleDateFormat("mm:ss", Locale.US)
        timerCalendar = Calendar.getInstance()
        super.onCreate(savedInstanceState)
    }

    @OnClick(R.id.btn_play_pause)
    fun onPlayPauseClicked() {
        val intent = Intent(context, TimerService::class.java)
        intent.putExtra(TimerService.KEY_TOURNAMENT, 1)
        context?.startService(intent)
    }

    override fun onStart() {
        EventBus.getDefault().register(this)
        super.onStart()
    }

    override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
    }

    @Subscribe
    fun onTimerTickEvent(tickEvent: TickEvent) {
        timerCalendar!!.timeInMillis = tickEvent.millisUntilFinished
        tvTimer.text = timeFormat!!.format(timerCalendar!!.time)
        ivTimer.setLevelCurrentTime(tickEvent.millisUntilFinished)
    }

    @Subscribe
    fun onTimerFinishedEvent(timerFinishedEvent: TimerFinishedEvent) {
        tvTimer.text = "00:00"
        ivTimer.setLevelCurrentTime(0)
    }

    companion object {
        fun newInstance(): TimerFragment {
            val args = Bundle()
            val fragment = TimerFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
