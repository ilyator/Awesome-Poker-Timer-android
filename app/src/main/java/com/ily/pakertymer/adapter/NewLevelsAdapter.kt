package com.ily.pakertymer.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ily.pakertymer.R
import com.ily.pakertymer.database.model.Level
import kotlinx.android.synthetic.main.row_new_level.view.*

/**
 * Created by ily on 11.03.2018.
 */
class NewLevelsAdapter(private val items: List<Level>)
    : RecyclerView.Adapter<NewLevelsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val rootView = LayoutInflater.from(parent?.context).inflate(R.layout.row_new_level, parent, false)
        return ViewHolder(rootView)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bindLevel(items[position])
    }

    class ViewHolder(root: View) : RecyclerView.ViewHolder(root) {

        fun bindLevel(level: Level){
            with(level){
                itemView.apply {
                    etDuration.setText(level.duration.toString())
                    etSmallBlind.setText(level.smallBlind.toString())
                    etBigBlind.setText(level.bigBlind.toString())
                    etAnte.setText(level.ante.toString())
                }
            }
        }

    }

}