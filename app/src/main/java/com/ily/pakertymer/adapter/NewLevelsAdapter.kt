package com.ily.pakertymer.adapter

import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ily.pakertymer.R
import com.ily.pakertymer.database.model.Level
import kotlinx.android.synthetic.main.row_new_level.view.*

/**
 * Created by ily on 11.03.2018.
 */
class NewLevelsAdapter(private val items: MutableList<Level>)
    : RecyclerView.Adapter<NewLevelsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val rootView = LayoutInflater.from(parent.context).inflate(R.layout.row_new_level, parent, false)
        return ViewHolder(rootView)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindLevel(items[position])
    }

    class ViewHolder(root: View) : RecyclerView.ViewHolder(root) {
        fun bindLevel(level: Level) {
            with(level) {
                itemView.apply {
                    etDuration.setText(duration.toString())
                    etSmallBlind.setText(smallBlind.toString())
                    etBigBlind.setText(bigBlind.toString())
                    etAnte.setText(ante.toString())
                    etDuration.addTextChangedListener(object : TextWatcher {
                        override fun afterTextChanged(s: Editable?) {
                            duration = if (s.isNullOrBlank())
                                0
                            else
                                s.toString().toLong()

                        }

                        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                        }

                        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                        }
                    })
                    etSmallBlind.addTextChangedListener(object : TextWatcher {
                        override fun afterTextChanged(s: Editable?) {
                            smallBlind = if (s.isNullOrBlank())
                                0
                            else
                                s.toString().toInt()
                        }

                        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                        }

                        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                        }
                    })
                    etBigBlind.addTextChangedListener(object : TextWatcher {
                        override fun afterTextChanged(s: Editable?) {
                            bigBlind = if (s.isNullOrBlank())
                                0
                            else
                                s.toString().toInt()
                        }

                        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                        }

                        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                        }
                    })
                    etAnte.addTextChangedListener(object : TextWatcher {
                        override fun afterTextChanged(s: Editable?) {
                            ante = if (s.isNullOrBlank())
                                0
                            else
                                s.toString().toInt()
                        }

                        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                        }

                        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                        }
                    })
                }
            }
        }
    }

}