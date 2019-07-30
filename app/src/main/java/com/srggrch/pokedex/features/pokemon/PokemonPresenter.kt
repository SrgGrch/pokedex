package com.srggrch.pokedex.features.pokemon

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.srggrch.pokedex.App
import com.srggrch.pokedex.core.NamedResHelper
import com.srggrch.pokedex.model.Pokemon
import com.srggrch.pokedex.model.PokemonAbility
import com.srggrch.pokedex.model.PokemonStat
import com.srggrch.pokedex.model.PokemonType
import com.srggrch.pokedex.network.api.PokemonApi
import retrofit2.Retrofit
import java.util.ArrayList
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
        loadAbilities(pokemon.abilities)
        loadStats(pokemon.stats)
        loadTypes(pokemon.types)
        loadWeightAndHeight(pokemon.weight, pokemon.height)
    }

    private fun loadWeightAndHeight(weight: Float, height: Float) {
        viewState.setWeightAndHeight(weight, height)
    }

    private fun loadTypes(types: ArrayList<PokemonType>) {
        var isFirst = true
        types.forEach {
            viewState.setType(it.type.name, isFirst)
            isFirst = false
        }
    }

    private fun loadStats(stats: ArrayList<PokemonStat>) {
        stats.forEach { viewState.setStat(it) }
    }

    private suspend fun loadAbilities(abilities: ArrayList<PokemonAbility>) {
        abilities.forEach {
            val desc = NamedResHelper().getAbility(it.ability).effect_entries[0].short_effect
            viewState.setAbility(it.ability.name, desc, it.is_hidden)
            viewState.hideAbilityProgress(abilities.size)
        }
    }


}
