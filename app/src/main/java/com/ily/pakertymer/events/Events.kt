package com.ily.pakertymer.events

import com.ily.pakertymer.database.model.TournamentWithLevels

/**
 * Created by ily on 24-Feb-18.
 */

class TickEvent(val millisUntilFinished: Long)

class TimerFinishedEvent

class TournamentStartedEvent(val tournamentWithLevels: TournamentWithLevels)