package com.ily.pakertymer.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.widget.LinearLayout
import com.ily.pakertymer.R
import com.ily.pakertymer.adapter.NewLevelsAdapter
import com.ily.pakertymer.database.model.Level
import kotlinx.android.synthetic.main.activity_new_tournament.*
import kotlinx.android.synthetic.main.toolbar.*

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
        for (i in 0..10) {
            levels.add(Level(0, 0, 0, 0, 0, 0))
        }
        rvLevels.layoutManager = LinearLayoutManager(this)//, LinearLayout.VERTICAL, false)
        rvLevels.adapter = NewLevelsAdapter(levels)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home)
            onBackPressed()
        return super.onOptionsItemSelected(item)
    }
}