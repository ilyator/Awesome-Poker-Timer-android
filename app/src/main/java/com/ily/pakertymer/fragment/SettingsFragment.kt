package com.ily.pakertymer.fragment

import android.os.Bundle
import android.support.v4.app.Fragment

/**
 * Created by ily on 20.10.2016.
 */

class SettingsFragment : Fragment() {
    companion object Factory {
        fun newInstance(): SettingsFragment {
            val args = Bundle()
            val fragment = SettingsFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
