package com.srggrch.testapp.core

import com.srggrch.testapp.App
import com.srggrch.testapp.model.NamedAPIResource
import com.srggrch.testapp.model.Pokemon
import com.srggrch.testapp.network.api.ResourceApi

class NamedResHelper<T> {

        val retrofit = App.INSTANCE.getAppComponent().apiModule

        suspend fun getResource(resource: NamedAPIResource): Pokemon {
            val api = retrofit.create(ResourceApi::class.java)
            return api.getResource<Pokemon>(UrlCutter.cut(resource.url))
        }

}