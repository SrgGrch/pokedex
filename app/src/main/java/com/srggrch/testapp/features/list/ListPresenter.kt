package com.srggrch.testapp.features.list

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.srggrch.testapp.App
import com.srggrch.testapp.core.NamedResHelper
import com.srggrch.testapp.model.NamedAPIResource
import com.srggrch.testapp.model.Pokemon
import com.srggrch.testapp.network.api.PokemonApi
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
        val result = api.getPokemonList()
        viewState.refreshTripList(result.results)
    }

    suspend fun loadNewItems() {
        val result = api.getPokemonList(page)
        viewState.addTripList(result.results)
        page += 30
    }

    suspend fun shuffleList() {
        val count = api.getPokemonList(0, 1).count
        page = Random.nextInt(0, count - 30)
        val result = api.getPokemonList(page)
        viewState.refreshTripList(result.results)
    }


    suspend fun sortList(list: ArrayList<NamedAPIResource>, attack: Boolean, defense: Boolean, hp: Boolean) {
        if (attack || defense || hp) {
            val pList = ArrayList<Pokemon>()

            val res = list.map{
                GlobalScope.async {
                    NamedResHelper().getPokemon(it)
                }
            }

            res.forEach{ pList.add(it.await()) }

            val max = pList.maxWith(
                getComparator(attack, defense, hp)
            )!!

            viewState.showTopPokemon(list.find { it.name == max.name }!!)
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