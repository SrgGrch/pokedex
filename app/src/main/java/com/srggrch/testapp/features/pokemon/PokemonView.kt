package com.srggrch.testapp.features.pokemon

import com.arellomobile.mvp.MvpView
import com.srggrch.testapp.model.Ability

interface PokemonView: MvpView {
    fun loadPicture(url: String)
    fun setAbility(name: String, desc: String, isHidden: Boolean)
    fun hideAbilityProgress(ammount: Int)

}
