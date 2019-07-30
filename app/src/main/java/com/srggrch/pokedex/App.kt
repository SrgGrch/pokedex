package com.srggrch.pokedex

import android.app.Application
import android.content.Context
import com.srggrch.pokedex.core.AppComponent
import com.srggrch.pokedex.core.DaggerAppComponent
import com.srggrch.pokedex.core.modules.SharedPreferencesModule
import com.srggrch.pokedex.network.RetrofitProvider





class App: Application(){
    private val retrofitProvider: RetrofitProvider = RetrofitProvider()

    private lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        component = DaggerAppComponent.builder()
            .sharedPreferencesModule(SharedPreferencesModule(this))
            .build()
    }

    fun getAppComponent() = component

    companion object {
        lateinit var INSTANCE: App

        private fun getApp(context: Context): App {
            return context.applicationContext as App
        }
    }
}
