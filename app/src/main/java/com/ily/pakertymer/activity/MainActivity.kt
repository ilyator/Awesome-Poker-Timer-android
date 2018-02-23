package com.ily.pakertymer.activity

import android.os.Bundle
import android.support.annotation.IdRes
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.ily.pakertymer.R
import com.ily.pakertymer.fragment.SavedFragment
import com.ily.pakertymer.fragment.SettingsFragment
import com.ily.pakertymer.fragment.TimerFragment
import com.roughike.bottombar.OnTabSelectListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private var fragment: Fragment? = null
    private var currentTabId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomBar.setOnNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val fragmentManager = supportFragmentManager
        fragment = when(item.itemId){
            R.id.tab_saved -> SavedFragment.newInstance()
            R.id.tab_timer -> TimerFragment.newInstance()
            R.id.tab_settings -> SettingsFragment.newInstance()
            else -> SavedFragment.newInstance()
        }
        fragmentManager
                .beginTransaction()
                .replace(R.id.contentContainer, fragment, fragment!!.javaClass.name)
                .commit()
        return true
    }

}
