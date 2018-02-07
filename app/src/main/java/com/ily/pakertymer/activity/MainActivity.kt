package com.ily.pakertymer.activity

import android.os.Bundle
import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.ily.pakertymer.R
import com.ily.pakertymer.fragment.SavedFragment
import com.ily.pakertymer.fragment.SettingsFragment
import com.ily.pakertymer.fragment.TimerFragment
import com.roughike.bottombar.OnTabSelectListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), OnTabSelectListener {

    private var fragment: Fragment? = null
    private var currentTabId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomBar.setOnTabSelectListener(this)
    }

    override fun onTabSelected(@IdRes tabId: Int) {
        val fragmentManager = supportFragmentManager
        if (currentTabId != tabId) {
            currentTabId = tabId
            when (tabId) {
                R.id.tab_saved -> fragment = SavedFragment.newInstance()
                R.id.tab_timer -> fragment = TimerFragment.newInstance()
                R.id.tab_settings -> fragment = SettingsFragment.newInstance()
            }
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.contentContainer, fragment, fragment!!.javaClass.name)
                    .commit()
        }
    }

}
