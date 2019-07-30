package com.srggrch.pokedex.network.api

import com.srggrch.pokedex.model.NamedAPIResource
import com.srggrch.pokedex.model.Wrapper
import retrofit2.http.GET
import retrofit2.http.Query

interface PokemonApi {
    @GET("/api/v2/pokemon")
    suspend fun getPokemonList(
        @Query("offset") offset: Int = 0,
        @Query("limit") limit: Int = 30
    ): Wrapper<NamedAPIResource>
}