package com.srggrch.testapp

import android.app.Application
import android.content.Context
import com.srggrch.testapp.core.AppComponent
import com.srggrch.testapp.core.DaggerAppComponent
import com.srggrch.testapp.core.modules.SharedPreferencesModule
import com.srggrch.testapp.network.RetrofitProvider





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
