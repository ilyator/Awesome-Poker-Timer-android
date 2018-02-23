package com.ily.pakertymer.adapter

import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.ily.pakertymer.R
import com.ily.pakertymer.database.model.Tournament
import com.ily.pakertymer.listener.ItemTouchHelperAdapter
import kotlinx.android.synthetic.main.item_tournament.view.*

/**
 * Created by ily on 21.10.2016.
 */

class SavedTourneysAdapter(private val tournaments: List<Tournament>,
                           private val onPlayClick: () -> Unit)
    : RecyclerView.Adapter<SavedTourneysAdapter.ViewHolder>(), ItemTouchHelperAdapter {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val rootView = LayoutInflater.from(parent.context).inflate(R.layout.item_tournament, parent, false)
        return ViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindTournament(tournaments[position])
    }

    override fun getItemCount(): Int {
        return tournaments.size
    }

    override fun onItemDismiss(position: Int) {
        //TODO: delete from db + add UNDO snackbar
        notifyItemRemoved(position)
    }

    inner class ViewHolder(root: View) : RecyclerView.ViewHolder(root) {

        private var activeBtn: FloatingActionButton? = null

        fun bindTournament(tournament: Tournament?) {
            if (tournament != null) {
                with(tournament) {
                    itemView.apply {
                        tvName.text = tournament.name
                        // tvBlinds.text = TournamentsUtil.getBlindsInfo(context, tournament)
                        //tvLevels.text = TournamentsUtil.getLevelsInfo(context, tournament)
                        btnPlayPause.setOnClickListener {
                            onPlayClick.invoke()
                        }
                        cardView.setOnClickListener { Toast.makeText(context, "Open Tournament", Toast.LENGTH_SHORT).show() }
                    }
                }
                setPlayBtn(tournament, itemView.btnPlayPause)
            }
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