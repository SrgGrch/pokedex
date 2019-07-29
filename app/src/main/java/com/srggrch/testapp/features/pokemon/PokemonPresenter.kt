package com.srggrch.testapp.features.pokemon

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.srggrch.testapp.App
import com.srggrch.testapp.model.Pokemon
import com.srggrch.testapp.network.api.PokemonApi
import retrofit2.Retrofit
import javax.inject.Inject

@InjectViewState
class PokemonPresenter: MvpPresenter<PokemonView>() {

    @Inject
    lateinit var retrofit: Retrofit

    val api:PokemonApi

    init {
        App.INSTANCE.getAppComponent().inject(this)
        api = retrofit.create(PokemonApi::class.java)
    }


    suspend fun loadPokemon(pokemon: Pokemon) {
        viewState.loadPicture(pokemon.sprites.front_default)
    }


}
