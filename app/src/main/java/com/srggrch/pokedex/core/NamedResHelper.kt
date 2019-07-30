package com.srggrch.pokedex.core

import com.srggrch.pokedex.App
import com.srggrch.pokedex.model.Ability
import com.srggrch.pokedex.model.NamedAPIResource
import com.srggrch.pokedex.model.Pokemon
import com.srggrch.pokedex.network.api.ResourceApi

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