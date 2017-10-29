package com.ily.pakertymer.adapter

import android.content.Context
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.ily.pakertymer.R
import com.ily.pakertymer.listener.ItemTouchHelperAdapter
import com.ily.pakertymer.model.Tournament
import com.ily.pakertymer.util.TournamentsUtil
import io.realm.Realm
import io.realm.RealmResults

/**
 * Created by ily on 21.10.2016.
 */

class SavedTourneysAdapter(private val context: Context, private val tournaments: RealmResults<Tournament>)
    : RecyclerView.Adapter<SavedTourneysAdapter.ViewHolder>(), ItemTouchHelperAdapter {

    private val realm: Realm = Realm.getDefaultInstance()
    private var activeBtn: FloatingActionButton? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val rootView = LayoutInflater.from(parent.context).inflate(R.layout.item_tournament, parent, false)
        return ViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tournament = tournaments[position]
        holder.tvName.text = tournament!!.name
        holder.tvBlinds.text = TournamentsUtil.getBlindsInfo(context, tournament)
        holder.tvLevels.text = TournamentsUtil.getLevelsInfo(context, tournament)
        setPlayBtn(tournament, holder.btnPlayPause)
        holder.btnPlayPause.setOnClickListener {
            realm.executeTransaction({
                for (t in tournaments) {
                    if (t.index != tournament.index) {
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
            setPlayBtn(tournament, holder.btnPlayPause)
        }
        holder.root.setOnClickListener { Toast.makeText(context, "Open Tournament", Toast.LENGTH_SHORT).show() }
    }

    private fun setPlayBtn(tournament: Tournament, btn: FloatingActionButton) {
        if (tournament.isRunning) {
            activeBtn = btn
            btn.setImageResource(R.drawable.ic_pause_white_24dp)
        } else
            btn.setImageResource(R.drawable.ic_play_arrow_white_24dp)
    }

    override fun getItemCount(): Int {
        return tournaments.size
    }

    override fun onItemDismiss(position: Int) {
        realm.executeTransaction {
            try {
                tournaments[position]!!.deleteFromRealm()
                notifyItemRemoved(position)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    class ViewHolder(var root: View) : RecyclerView.ViewHolder(root) {
        var tvName: TextView = root.findViewById(R.id.tv_name)
        var tvBlinds: TextView = root.findViewById(R.id.tv_blinds)
        var tvLevels: TextView = root.findViewById(R.id.tv_levels)
        var btnPlayPause: FloatingActionButton = root.findViewById(R.id.btn_play_pause)
    }

}