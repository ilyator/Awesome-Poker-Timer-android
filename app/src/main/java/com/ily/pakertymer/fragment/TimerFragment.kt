package com.ily.pakertymer.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ily.pakertymer.Constants.SP_KEY_TOURNAMENT
import com.ily.pakertymer.R
import com.ily.pakertymer.database.model.TournamentWithLevels
import com.ily.pakertymer.events.TickEvent
import com.ily.pakertymer.events.TimerFinishedEvent
import com.ily.pakertymer.service.TimerService
import kotlinx.android.synthetic.main.fragment_timer.*
import kotlinx.android.synthetic.main.layout_level_navigation.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by ily on 20.10.2016.
 */

class TimerFragment : Fragment() {

    private val timeFormat = SimpleDateFormat("mm:ss", Locale.US)
    private val timerCalendar = Calendar.getInstance()
    private var tournamentWithLevels: TournamentWithLevels? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tournamentWithLevels = arguments?.getParcelable(SP_KEY_TOURNAMENT)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_timer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tournamentWithLevels?.tournament?.currentLevel = tournamentWithLevels?.levels?.get(0)
        tournamentWithLevels?.tournament?.currentLevel?.duration?.let {
            ivTimer.setLevelFullTime(it)
            ivTimer.setLevelCurrentTime(it)
        }

        setOnClickListeners()
        btnPlayPause.performClick()
    }

    private fun setOnClickListeners() {
        btnPlayPause.setOnClickListener {
            val intent = Intent(context, TimerService::class.java)
            intent.putExtra(SP_KEY_TOURNAMENT, tournamentWithLevels)
            context?.startService(intent)
        }
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
        timerCalendar.timeInMillis = tickEvent.millisUntilFinished
        tvTimer.text = timeFormat.format(timerCalendar.time)
        ivTimer.setLevelCurrentTime(tickEvent.millisUntilFinished)
    }

    @Subscribe
    fun onTimerFinishedEvent(timerFinishedEvent: TimerFinishedEvent) {
        tvTimer.text = "00:00"
        ivTimer.setLevelCurrentTime(0)
    }

    companion object {
        fun newInstance(tournamentWithLevels: TournamentWithLevels): TimerFragment {
            val args = Bundle()
            args.putParcelable(SP_KEY_TOURNAMENT, tournamentWithLevels)
            val fragment = TimerFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
