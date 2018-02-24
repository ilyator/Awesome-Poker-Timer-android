package com.ily.pakertymer.fragment

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ily.pakertymer.R
import com.ily.pakertymer.TimerApp
import com.ily.pakertymer.adapter.SavedTourneysAdapter
import com.ily.pakertymer.database.model.TournamentWithLevels
import com.ily.pakertymer.events.TournamentStartedEvent
import kotlinx.android.synthetic.main.fragment_saved.*
import org.greenrobot.eventbus.EventBus

/**
 * Created by ily on 20.10.2016.
 */

class SavedFragment : Fragment() {

    lateinit var adapter: SavedTourneysAdapter
    private val tournamentDao = TimerApp.database.tournamentDao

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_saved, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tournamentDao.getTournamentsWithLevelsAsync().observe(this, Observer { tournaments ->
            tournaments?.let { setUpRecyclerView(it) }
        })
    }

    private fun setUpRecyclerView(tournamentWithLevels: List<TournamentWithLevels>) {
        adapter = SavedTourneysAdapter(tournamentWithLevels, {
            EventBus.getDefault().post(TournamentStartedEvent(it))
        })
        rvSaved.layoutManager = LinearLayoutManager(context)
        rvSaved.adapter = adapter
    }

    companion object {
        fun newInstance(): SavedFragment {
            val args = Bundle()
            val fragment = SavedFragment()
            fragment.arguments = args
            return fragment
        }
    }

}
