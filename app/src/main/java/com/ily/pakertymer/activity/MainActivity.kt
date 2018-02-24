package com.ily.pakertymer.activity

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.ily.pakertymer.R
import com.ily.pakertymer.events.TournamentStartedEvent
import com.ily.pakertymer.fragment.SavedFragment
import com.ily.pakertymer.fragment.SettingsFragment
import com.ily.pakertymer.fragment.TimerFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener, BottomNavigationView.OnNavigationItemReselectedListener {

    private var fragment: Fragment = SavedFragment.newInstance()
    private var creation = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomBar.setOnNavigationItemSelectedListener(this)
        bottomBar.setOnNavigationItemReselectedListener(this)
        bottomBar.selectedItemId = R.id.tab_saved
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        fragment = when (item.itemId) {
            R.id.tab_saved -> SavedFragment.newInstance()
            R.id.tab_timer -> SavedFragment.newInstance()
            R.id.tab_settings -> SettingsFragment.newInstance()
            else -> SavedFragment.newInstance()
        }
        replaceFragment(fragment)

        return true
    }

    override fun onNavigationItemReselected(item: MenuItem) {
        if (creation ) {
            creation = false
            onNavigationItemSelected(item)
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                .replace(R.id.contentContainer, fragment, fragment.javaClass.name)
                .commit()
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onTournamentStarted(event: TournamentStartedEvent){
        replaceFragment(TimerFragment.newInstance(event.tournamentWithLevels))
    }

}
