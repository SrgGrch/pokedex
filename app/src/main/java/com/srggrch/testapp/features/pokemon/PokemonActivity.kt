package com.srggrch.testapp.features.pokemon

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import com.srggrch.testapp.R
import com.srggrch.testapp.core.moxy.MvpAndroidxActivity
import com.srggrch.testapp.model.NamedAPIResource
import com.srggrch.testapp.model.Pokemon
import kotlinx.android.synthetic.main.activity_pokemon.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PokemonActivity : MvpAndroidxActivity(), PokemonView {

    @InjectPresenter
    lateinit var presenter: PokemonPresenter

    lateinit var pokemon: Pokemon

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon)

        pokemon = Gson().fromJson(intent.getStringExtra("Pokemon"), Pokemon::class.java)

        val text = pokemon.name.substring(0, 1).toUpperCase() + pokemon.name.substring(1)
        title = text
        name.text = text

        GlobalScope.launch(Dispatchers.Main) {
            presenter.loadPokemon(pokemon)
        }


    }

    override fun loadPicture(url: String) {
        Picasso
            .get()
            .load(url)
            .resize(200, 200)
            .into(photo)
    }

    companion object {
        fun start(pokemon: Pokemon, context: Context) {
            val intent = Intent(context, PokemonActivity::class.java)
            val extra = Gson().toJson(pokemon, Pokemon::class.java)
            intent.putExtra("Pokemon", extra)
            context.startActivity(intent)
        }
    }

}