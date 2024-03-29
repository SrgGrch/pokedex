package com.srggrch.pokedex.features.list

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.srggrch.pokedex.R
import com.srggrch.pokedex.core.PaginationScrollListener
import com.srggrch.pokedex.core.PokemonDiffUtillCallback
import com.srggrch.pokedex.core.moxy.MvpAndroidxActivity
import com.srggrch.pokedex.features.pokemon.PokemonActivity
import com.srggrch.pokedex.model.Pokemon
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList


class ListActivity : MvpAndroidxActivity(), ListView {

    @InjectPresenter
    lateinit var presenter: ListPresenter

    private lateinit var recyclerView: RecyclerView
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapter: ListAdapter

    private var attack = false
    private var hp = false
    private var defence = false

    var isLoading = false

    private var isFABOpen = false


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
                isLoading = true
                GlobalScope.launch(Dispatchers.Main) {
                    presenter.loadNewItems()
                }
            }

            override fun isLastPage(): Boolean = false

            override fun isLoading(): Boolean = isLoading

        })

        adapter = ListAdapter(object : ListAdapter.ItemClickListener {
            override fun onItemClickListener(pokemon: Pokemon) {
                PokemonActivity.start(pokemon, this@ListActivity)
            }
        })

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        shuffleFab.setOnClickListener {
            GlobalScope.launch(Dispatchers.Main) {
                presenter.shuffleList()
            }

        }

        GlobalScope.launch(Dispatchers.Main) {
            presenter.loadNewItems()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.list_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.itemAttack -> {
                item.isChecked = !item.isChecked
                attack = item.isChecked
                sortList()
                true
            }
            R.id.itemDefence ->{
                item.isChecked = !item.isChecked
                defence = item.isChecked
                sortList()
                true
            }
            R.id.itemHP -> {
                item.isChecked = !item.isChecked
                hp = item.isChecked
                sortList()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun sortList() {
        progressView.visibility = View.VISIBLE
        recyclerView.isFocusable = false
        GlobalScope.launch(Dispatchers.Main) {
            presenter.sortList(adapter.items,attack, defence, hp)
        }

    }

    override fun refreshTripList(list: ArrayList<Pokemon>) {
        adapter.refreshItems(list)
        mainRefresh.isRefreshing = false
        progressView.visibility = View.GONE
        layoutManager.scrollToPositionWithOffset(0, 0)
    }

    override fun addItems(list: ArrayList<Pokemon>) {
        adapter.setData(list)
        isLoading = false
//        adapter.notifyDataSetChanged()
    }

    override fun showSortedList(sortedList: ArrayList<Pokemon>) {
        val callback = PokemonDiffUtillCallback(adapter.items, sortedList)
        val result = DiffUtil.calculateDiff(callback)
        adapter.setSortedData(sortedList)

        result.dispatchUpdatesTo(adapter)

        layoutManager.scrollToPositionWithOffset(0, 0)
        progressView.visibility = View.GONE
    }

    @Deprecated("old method, use fun showSortedList(...) instead")
    override fun showTopPokemon(pokemon: Pokemon) {
        val position = adapter.items.indexOf(pokemon)
        Collections.swap(adapter.items, position, 0)
        adapter.notifyItemMoved(position, 0)
        layoutManager.scrollToPositionWithOffset(0, 0)
        recyclerView.isFocusable = true
        progressView.visibility = View.GONE
    }

}
