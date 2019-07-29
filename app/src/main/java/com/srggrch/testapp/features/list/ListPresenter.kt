package com.srggrch.testapp.features.list

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.srggrch.testapp.App
import com.srggrch.testapp.network.api.PokemonApi
import retrofit2.Retrofit
import javax.inject.Inject

@InjectViewState
class ListPresenter: MvpPresenter<ListView>() {

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
        val result = api.getPokemonList(page * 30)
        viewState.addTripList(result.results)
        page++
    }
}