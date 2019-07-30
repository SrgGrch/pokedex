package com.srggrch.testapp.core

import androidx.recyclerview.widget.DiffUtil
import com.srggrch.testapp.model.Pokemon

class PokemonDiffUtillCallback(
    private val oldList: List<Pokemon>,
    private val newList: List<Pokemon>
) : DiffUtil.Callback() {


    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldPokemon = oldList[oldItemPosition]
        val newPokemon = newList[newItemPosition]
        return oldPokemon.name === newPokemon.name
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldPokemon = oldList[oldItemPosition]
        val newPokemon = newList[newItemPosition]
        return oldPokemon == newPokemon
    }
}