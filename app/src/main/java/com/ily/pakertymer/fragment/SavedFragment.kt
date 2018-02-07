package com.ily.pakertymer.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ily.pakertymer.R
import com.ily.pakertymer.adapter.SavedTourneysAdapter
import com.ily.pakertymer.adapter.SimpleItemTouchHelperCallback
import com.ily.pakertymer.model.Tournament
import io.realm.Realm
import io.realm.Sort
import kotlinx.android.synthetic.main.fragment_saved.*

/**
 * Created by ily on 20.10.2016.
 */

class SavedFragment : Fragment() {

    private var realm: Realm = Realm.getDefaultInstance()
    lateinit var adapter: SavedTourneysAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_saved, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        val tournaments = realm.where(Tournament::class.java).findAllSorted("index", Sort.ASCENDING)
        adapter = SavedTourneysAdapter(context!!, tournaments)
        rvSaved.setHasFixedSize(true)
        val manager = LinearLayoutManager(context)
        manager.orientation = LinearLayoutManager.VERTICAL
        rvSaved.layoutManager = manager
        rvSaved.adapter = adapter
        val callback = SimpleItemTouchHelperCallback(adapter)
        val touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(rvSaved)
        rvSaved.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                if (dy > 0 && btnAdd.isShown)
                    btnAdd.hide()
                if (dy < 0 && !btnAdd.isShown)
                    btnAdd.show()
            }
        })
    }

    companion object {

        fun newInstance(): SavedFragment {
            val args = Bundle()
            val fragment = SavedFragment()
            fragment.arguments = args
            return fragment
        }
    }

}
