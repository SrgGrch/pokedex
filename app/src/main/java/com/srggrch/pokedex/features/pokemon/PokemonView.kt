package com.srggrch.pokedex.features.pokemon

import com.arellomobile.mvp.MvpView
import com.srggrch.pokedex.model.PokemonStat

interface PokemonView: MvpView {
    fun loadPicture(url: String)
    fun setAbility(name: String, desc: String, isHidden: Boolean)
    fun hideAbilityProgress(amount: Int)
    fun setStat(pokemonStat: PokemonStat)
    fun setType(name: String, isFirst: Boolean)
    fun setWeightAndHeight(weight: Float, height: Float)

}
