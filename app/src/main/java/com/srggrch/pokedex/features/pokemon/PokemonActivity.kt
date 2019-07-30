package com.srggrch.pokedex.features.pokemon

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.core.text.toSpannable
import com.arellomobile.mvp.presenter.InjectPresenter
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import com.srggrch.pokedex.R
import com.srggrch.pokedex.core.moxy.MvpAndroidxActivity
import com.srggrch.pokedex.model.Pokemon
import com.srggrch.pokedex.model.PokemonStat
import kotlinx.android.synthetic.main.activity_pokemon.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PokemonActivity : MvpAndroidxActivity(), PokemonView {

    @InjectPresenter
    lateinit var presenter: PokemonPresenter

    lateinit var pokemon: Pokemon

    var abilitiesCount = 0

    var totalStat = 0

    var statCounter = 0

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

    override fun hideAbilityProgress(amount: Int){
        abilitiesCount++
        if (abilitiesCount == amount){
            abilitiesBar.visibility = View.GONE
            abilitiesLayout.visibility = View.VISIBLE
        }
    }

    override fun setStat(pokemonStat: PokemonStat) {
        totalStat += pokemonStat.base_stat
        val concat: (String, Int) -> String = {x, y -> "$x $y" }
        when (pokemonStat.stat.name){
            "speed" -> statSpeed.text = concat(statSpeed.text.toString(), pokemonStat.base_stat)
            "special-defense" -> statSpecialDefense.text = concat(statSpecialDefense.text.toString(), pokemonStat.base_stat)
            "special-attack" -> statSpecialAttack.text = concat(statSpecialAttack.text.toString(), pokemonStat.base_stat)
            "defense" -> statDefense.text = concat(statDefense.text.toString(), pokemonStat.base_stat)
            "attack" -> statAttack.text = concat(statAttack.text.toString(), pokemonStat.base_stat)
            "hp" -> statHP.text = concat(statHP.text.toString(), pokemonStat.base_stat)
        }
        statTotal.text = concat(resources.getString(R.string.stat_total), totalStat)
        statCounter++
        if (statCounter == 6) {
            statBar.visibility = View.GONE
            statLayout.visibility = View.VISIBLE
        }
    }

    override fun setType(name: String, isFirst: Boolean) {
        val concat: (String, String, String) -> String = {x, y, z -> "$x$y $z" }
        types.text = if (isFirst) concat(types.text.toString(), "", name) else concat(types.text.toString(), ",", name)
    }

    override fun setWeightAndHeight(weight: Float, height: Float) {
        val concat: (String, Float) -> String = {x, y -> "$x $y" }
        this.weight.text = concat(this.weight.text.toString(), (weight / 10))
        this.height.text = concat(this.height.text.toString(), (height / 10))
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