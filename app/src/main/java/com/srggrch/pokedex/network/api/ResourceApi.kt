package com.srggrch.pokedex.network.api

import com.srggrch.pokedex.model.Ability
import com.srggrch.pokedex.model.Pokemon
import retrofit2.http.GET
import retrofit2.http.Url

interface ResourceApi {

    @GET
    suspend fun getPokemon(@Url url: String): Pokemon

    @GET
    suspend fun getAbility(@Url url: String): Ability

}