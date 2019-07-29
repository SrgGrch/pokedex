package com.srggrch.testapp.features.pokemon

import com.arellomobile.mvp.MvpView

interface PokemonView: MvpView {
    fun loadPicture(url: String)

}
