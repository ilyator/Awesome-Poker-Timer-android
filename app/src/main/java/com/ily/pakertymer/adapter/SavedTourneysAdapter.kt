package com.ily.pakertymer.adapter

import android.content.Context
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.ily.pakertymer.R
import com.ily.pakertymer.listener.ItemTouchHelperAdapter
import com.ily.pakertymer.database.model.Tournament
import io.realm.Realm
import io.realm.RealmResults
import kotlinx.android.synthetic.main.item_tournament.view.*

/**
 * Created by ily on 21.10.2016.
 */

class SavedTourneysAdapter(private val context: Context,
                           private val tournaments: List<Tournament>)
    : RecyclerView.Adapter<SavedTourneysAdapter.ViewHolder>(), ItemTouchHelperAdapter {

    private val realm: Realm = Realm.getDefaultInstance()

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
    /*    realm.executeTransaction {
            try {
                tournaments[position]!!.deleteFromRealm()
                notifyItemRemoved(position)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }*/
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
                            realm.executeTransaction({
                                for (t in tournaments) {
                                    if (t.id != tournament.id) {
                                        t.isActive = false
                                        t.isRunning = false
                                    } else {
                                        t.isRunning = !t.isRunning
                                        t.isActive = true
                                    }
                                }
                            })

                            if (activeBtn != null)
                                activeBtn!!.setImageResource(R.drawable.ic_play_arrow_white_24dp)
                            setPlayBtn(tournament, btnPlayPause)
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