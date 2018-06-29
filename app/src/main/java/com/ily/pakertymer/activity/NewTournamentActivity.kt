package com.ily.pakertymer.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import com.ily.pakertymer.R
import com.ily.pakertymer.TimerApp
import com.ily.pakertymer.database.model.Level
import com.ily.pakertymer.database.model.Tournament
import kotlinx.android.synthetic.main.activity_new_tournament.*
import kotlinx.android.synthetic.main.row_new_level.view.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

/**
 * Created by ily on 11.03.2018.
 */
class NewTournamentActivity : AppCompatActivity() {

    private val levels = ArrayList<Level>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_tournament)
        initToolBar()
        initLevels()
        setOnClickListeners()
    }

    private fun initToolBar() {
        setSupportActionBar(toolbar as Toolbar)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(true)
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
        setTitle(R.string.new_tournament)
    }

    private fun initLevels() {
        levels.add(Level(0, 0, 0, 0, 0, 0))
        levels.map { LayoutInflater.from(this).inflate(R.layout.row_new_level, llLevels, false) }
                .zip(levels)
                .forEach {
                    bindLevel(it.first, it.second)
                    llLevels.addView(it.first)
                }
        svLevels.fullScroll(View.FOCUS_DOWN)
    }

    private fun setOnClickListeners() {
        btnPresets.setOnClickListener {
            toast("Coming soon")
        }
        btnSave.setOnClickListener {
            if (etName.text.isNotBlank()) {
                doAsync {
                    val id = TimerApp.database.tournamentDao
                            .insert(Tournament(etName.text.toString()))
                    levels.forEach {
                        it.tournamentId = id
                        TimerApp.database.levelDao.insert(it)
                    }
                    uiThread {
                        toast(R.string.tournament_created)
                        finish()
                    }
                }
            } else {
                toast(R.string.fill_name)
            }
        }
        btnAddLevel.setOnClickListener {
            val lastLevel = levels.last()
            val newLevel = Level(lastLevel.duration,
                    lastLevel.smallBlind * 2,
                    lastLevel.bigBlind * 2,
                    lastLevel.ante * 2,
                    0)
            levels.add(newLevel)
            val tv = LayoutInflater.from(this).inflate(R.layout.row_new_level, llLevels, false)
            bindLevel(tv, newLevel)
            llLevels.addView(tv)
            svLevels.post {
                svLevels.smoothScrollTo(0, svLevels.height)
            }
        }
    }

    private fun bindLevel(v: View, level: Level) {
        with(level) {
            v.apply {
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

    private fun validateFields(){
        //TODO: validate input
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home)
            onBackPressed()
        return super.onOptionsItemSelected(item)
    }
}