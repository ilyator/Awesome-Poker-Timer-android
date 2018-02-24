package com.ily.pakertymer.adapter

import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.ily.pakertymer.R
import com.ily.pakertymer.database.model.Tournament
import com.ily.pakertymer.database.model.TournamentWithLevels
import com.ily.pakertymer.listener.ItemTouchHelperAdapter
import com.ily.pakertymer.util.TournamentsUtil
import kotlinx.android.synthetic.main.item_tournament.view.*

/**
 * Created by ily on 21.10.2016.
 */

class SavedTourneysAdapter(private val items: List<TournamentWithLevels>,
                           private val onPlayClick: (tournament: TournamentWithLevels) -> Unit)
    : RecyclerView.Adapter<SavedTourneysAdapter.ViewHolder>(), ItemTouchHelperAdapter {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val rootView = LayoutInflater.from(parent.context).inflate(R.layout.item_tournament, parent, false)
        return ViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindTournament(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onItemDismiss(position: Int) {
        //TODO: delete from db + add UNDO snackbar
        notifyItemRemoved(position)
    }

    inner class ViewHolder(root: View) : RecyclerView.ViewHolder(root) {

        private var activeBtn: FloatingActionButton? = null

        fun bindTournament(tournamentWithLevels: TournamentWithLevels) {
            with(tournamentWithLevels) {
                itemView.apply {
                    tvName.text = tournamentWithLevels.tournament?.name
                    tvBlinds.text = TournamentsUtil.getBlindsInfo(context, tournamentWithLevels)
                    tvLevels.text = TournamentsUtil.getLevelsInfo(context, tournamentWithLevels)
                    btnPlayPause.setOnClickListener {
                        onPlayClick.invoke(tournamentWithLevels)
                    }
                    cardView.setOnClickListener { Toast.makeText(context, "Open Tournament", Toast.LENGTH_SHORT).show() }
                }
            }
            setPlayBtn(tournamentWithLevels.tournament!!, itemView.btnPlayPause)
        }

        private fun setPlayBtn(tournament: Tournament, btn: FloatingActionButton) {
            if (tournament.isRunning) {
                activeBtn = btn
                btn.setImageResource(R.drawable.ic_pause_white_24dp)
            } else
                btn.setImageResource(R.drawable.ic_play_arrow_white_24dp)
        }
    }

}