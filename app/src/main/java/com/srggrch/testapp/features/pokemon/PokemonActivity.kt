package com.srggrch.testapp.features.pokemon

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.PersistableBundle
import android.text.Spannable
import android.text.SpannableString
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.core.text.toSpannable
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

    var abilitiesCount = 0

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

    override fun setAbility(name: String, desc: String, isHidden: Boolean) {
        if (isHidden) {
            val newName = name.substring(0, 1).toUpperCase() + name.substring(1)
            val spannable = SpannableString("$newName - $desc")
            spannable.setSpan(ForegroundColorSpan(Color.BLUE), 0, name.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            val text =
                TextUtils.concat(
                    hiddenAbilitiesCont.text.toSpannable(),
                    if (hiddenAbilitiesCont.text.isBlank()) spannable else "\n" + spannable
                )
            hiddenAbilities.visibility = View.VISIBLE
            hiddenAbilitiesCont.visibility = View.VISIBLE
            hiddenAbilitiesCont.text = text
        } else {
            val newName = name.substring(0, 1).toUpperCase() + name.substring(1)
            val spannable = SpannableString("$newName - $desc")
            spannable.setSpan(ForegroundColorSpan(Color.BLUE), 0, name.length, 0)
            val text =
                TextUtils.concat(
                    abilitiesCont.text.toSpannable(),
                    if (abilitiesCont.text.isBlank()) spannable else "\n" + spannable
                )
            abilitiesCont.text = text
        }
    }

    override fun hideAbilityProgress(ammount: Int){
        abilitiesCount++
        if (abilitiesCount == ammount){
            abilitiesBar.visibility = View.GONE
            abilitiesLayout.visibility = View.VISIBLE
        }
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