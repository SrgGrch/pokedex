package com.srggrch.testapp.features.list

import com.arellomobile.mvp.MvpView
import com.srggrch.testapp.model.NamedAPIResource
import com.srggrch.testapp.model.Pokemon

interface ListView : MvpView {

    fun addItems(list: ArrayList<NamedAPIResource>)

    fun refreshTripList(list: ArrayList<NamedAPIResource>)

    fun showTopPokemon(find: NamedAPIResource)
}