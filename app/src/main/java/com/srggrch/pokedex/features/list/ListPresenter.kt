package com.srggrch.pokedex.features.list

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.srggrch.pokedex.App
import com.srggrch.pokedex.core.NamedResHelper
import com.srggrch.pokedex.model.NamedAPIResource
import com.srggrch.pokedex.model.Pokemon
import com.srggrch.pokedex.network.api.PokemonApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import retrofit2.Retrofit
import java.util.ArrayList
import javax.inject.Inject
import kotlin.random.Random

@InjectViewState
class ListPresenter : MvpPresenter<ListView>() {

    val api: PokemonApi

    @Inject
    lateinit var retrofit: Retrofit

    var next: String = ""

    var page = 0

    init {
        App.INSTANCE.getAppComponent().inject(this)
        api = retrofit.create(PokemonApi::class.java)
    }


    suspend fun refresh() {
        val result = api.getPokemonList().results
        viewState.refreshTripList(convertList(result))
    }

    suspend fun loadNewItems() {
        val result = api.getPokemonList(page).results

        page += 30
        viewState.addItems(convertList(result))
    }

    suspend fun shuffleList() {
        val count = api.getPokemonList(0, 1).count
        page = Random.nextInt(0, count - 30)
        val result = api.getPokemonList(page).results
        viewState.refreshTripList(convertList(result))
    }

    suspend fun convertList(list: ArrayList<NamedAPIResource>): ArrayList<Pokemon> {
        val resList = ArrayList<Pokemon>()

        val listTask = list.map {
            GlobalScope.async {
                NamedResHelper().getPokemon(it)
            }
        }

        listTask.forEach { resList.add(it.await()) }

        return resList
    }


    suspend fun sortList(list: ArrayList<Pokemon>, attack: Boolean, defense: Boolean, hp: Boolean) {
        if (attack || defense || hp) {

            list.sortWith(
                getComparator(attack, defense, hp)
            )

            list.reverse()

            viewState.showSortedList(list)
        } else {
            refresh()
        }
    }

    private fun getComparator(attack: Boolean, defense: Boolean, hp: Boolean): Comparator<Pokemon> {
        if (attack && !defense && !hp) return compareBy { pokemon -> pokemon.stats.find { it.stat.name == "attack" }!!.base_stat } // 100
        if (!attack && defense && !hp) return compareBy { pokemon -> pokemon.stats.find { it.stat.name == "defense" }!!.base_stat } // 010
        if (!attack && !defense && hp) return compareBy { pokemon -> pokemon.stats.find { it.stat.name == "hp" }!!.base_stat } // 001
        if (!attack && defense && hp) return compareBy(
            { pokemon -> pokemon.stats.find { it.stat.name == "defense" }!!.base_stat },
            { pokemon -> pokemon.stats.find { it.stat.name == "hp" }!!.base_stat }
        ) // 011
        if (attack && !defense && hp) return compareBy(
            { pokemon -> pokemon.stats.find { it.stat.name == "attack" }!!.base_stat },
            { pokemon -> pokemon.stats.find { it.stat.name == "hp" }!!.base_stat }
        ) // 101
        if (attack && defense && !hp) return compareBy(
            { pokemon -> pokemon.stats.find { it.stat.name == "attack" }!!.base_stat },
            { pokemon -> pokemon.stats.find { it.stat.name == "defense" }!!.base_stat }
        ) // 110
        return compareBy(
            { pokemon -> pokemon.stats.find { it.stat.name == "attack" }!!.base_stat },
            { pokemon -> pokemon.stats.find { it.stat.name == "defense" }!!.base_stat },
            { pokemon -> pokemon.stats.find { it.stat.name == "hp" }!!.base_stat }
        ) // 111
    }
}