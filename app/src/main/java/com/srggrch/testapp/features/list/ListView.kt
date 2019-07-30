package com.srggrch.testapp.features.list

import com.arellomobile.mvp.MvpView
import com.srggrch.testapp.model.Pokemon

interface ListView : MvpView {

    fun addItems(list: ArrayList<Pokemon>)

    fun refreshTripList(list: ArrayList<Pokemon>)

    fun showTopPokemon(find: Pokemon)

    fun showSortedList(sortedList: ArrayList<Pokemon>)
}