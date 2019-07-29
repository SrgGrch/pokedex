package com.srggrch.testapp.core

import com.srggrch.testapp.App
import com.srggrch.testapp.model.Ability
import com.srggrch.testapp.model.NamedAPIResource
import com.srggrch.testapp.model.Pokemon
import com.srggrch.testapp.model.PokemonAbility
import com.srggrch.testapp.network.api.ResourceApi

class NamedResHelper {

    private val retrofit = App.INSTANCE.getAppComponent().apiModule

    suspend fun getPokemon(resource: NamedAPIResource): Pokemon {
        val api = retrofit.create(ResourceApi::class.java)
        return api.getPokemon(UrlCutter.cut(resource.url))
    }

    suspend fun getAbility(resource: NamedAPIResource): Ability {
        val api = retrofit.create(ResourceApi::class.java)
        return api.getAbility(UrlCutter.cut(resource.url))
    }

}