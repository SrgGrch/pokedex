package com.srggrch.testapp.features.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.srggrch.testapp.R
import com.srggrch.testapp.core.NamedResHelper
import com.srggrch.testapp.model.NamedAPIResource
import com.srggrch.testapp.model.Pokemon
import com.srggrch.testapp.network.api.ResourceApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import javax.inject.Inject

class ListAdapter(private val itemClickListener: ItemClickListener) :
    RecyclerView.Adapter<ListAdapter.ListHolder>() {

    private val items = ArrayList<NamedAPIResource>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ListHolder(itemView, itemClickListener)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ListHolder, position: Int) {
        holder.bind(items[position])
    }

    fun setItems(list: ArrayList<NamedAPIResource>) {
        if (list.isNotEmpty()) {
            items.addAll(list)
            notifyDataSetChanged()
        }
    }

    fun refreshItems(list: ArrayList<NamedAPIResource>) {
        if (list.isNotEmpty()) {
            items.clear()
            items.addAll(list)
            notifyDataSetChanged()
        }
    }


    inner class ListHolder(itemView: View, private var itemClickListener: ItemClickListener) :
        RecyclerView.ViewHolder(itemView) {

        @Inject
        lateinit var retrofit: Retrofit

        lateinit var api: ResourceApi

        private val name: TextView = itemView.findViewById(R.id.itemName)
        private val progress: ProgressBar = itemView.findViewById(R.id.itemProgressBar)
        private val cardView: CardView = itemView.findViewById(R.id.itemCardview)
        private val photo: ImageView = itemView.findViewById(R.id.itemPhoto)


        internal fun bind(pokemonRes: NamedAPIResource) {

            lateinit var pokemon: Pokemon

            val text = pokemonRes.name.substring(0, 1).toUpperCase() + pokemonRes.name.substring(1)

            name.text = text
            GlobalScope.launch(Dispatchers.Main) {
                pokemon = NamedResHelper().getPokemon(pokemonRes)
                Picasso
                    .get()
                    .load(pokemon.sprites.front_default)
                    .resize(200, 200)
                    .into(photo)
                photo.visibility = View.VISIBLE
                progress.visibility = View.GONE
            }
            cardView.setOnClickListener { itemClickListener.onItemClickListener(pokemon) }
        }
    }

    interface ItemClickListener {
        fun onItemClickListener(pokemon: Pokemon)
    }

}