package com.srggrch.testapp.features.list

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.srggrch.testapp.R
import com.srggrch.testapp.core.PaginationScrollListener
import com.srggrch.testapp.core.moxy.MvpAndroidxActivity
import com.srggrch.testapp.features.pokemon.PokemonActivity
import com.srggrch.testapp.model.NamedAPIResource
import com.srggrch.testapp.model.Pokemon
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ListActivity : MvpAndroidxActivity(), ListView {

    @InjectPresenter
    lateinit var presenter: ListPresenter

    private lateinit var recyclerView: RecyclerView
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapter: ListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = mainList

        mainRefresh.setOnRefreshListener {
            GlobalScope.launch(Dispatchers.Main) {
                presenter.refresh()
            }

        }

        layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        recyclerView.addOnScrollListener(object : PaginationScrollListener(layoutManager) {
            override fun loadMoreItems() {
                GlobalScope.launch(Dispatchers.Main) {
                    presenter.loadNewItems()
                }
            }

            override fun isLastPage(): Boolean = false

            override fun isLoading(): Boolean = false

        })

        adapter = ListAdapter(object : ListAdapter.ItemClickListener {
            override fun onItemClickListener(pokemon: Pokemon) {
                PokemonActivity.start(pokemon, this@ListActivity)
            }
        })

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        GlobalScope.launch(Dispatchers.Main) {
            presenter.loadNewItems()
        }

    }

    override fun refreshTripList(list: ArrayList<NamedAPIResource>) {
        adapter.refreshItems(list)
        mainRefresh.isRefreshing = false
    }

    override fun addTripList(list: ArrayList<NamedAPIResource>) {
        adapter.setItems(list)
//        adapter.notifyDataSetChanged()
    }

    override fun onListReady(list: List<Pokemon>) {
//        adapter.setItems(list)
    }

}
