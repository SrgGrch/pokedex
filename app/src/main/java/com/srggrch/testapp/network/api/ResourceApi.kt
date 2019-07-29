package com.srggrch.testapp.network.api

import com.srggrch.testapp.model.Pokemon
import retrofit2.http.GET
import retrofit2.http.Url

interface ResourceApi<in T> {
    @GET
    suspend fun <T: Pokemon> getResource(@Url url: String): Pokemon

//    @GET
//    suspend fun <T> getResource(@Url url: String): T
}