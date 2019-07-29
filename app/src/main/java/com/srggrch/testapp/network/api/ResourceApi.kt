package com.srggrch.testapp.network.api

import com.srggrch.testapp.model.Ability
import com.srggrch.testapp.model.Pokemon
import retrofit2.http.GET
import retrofit2.http.Url

interface ResourceApi {

    @GET
    suspend fun getPokemon(@Url url: String): Pokemon

    @GET
    suspend fun getAbility(@Url url: String): Ability

}