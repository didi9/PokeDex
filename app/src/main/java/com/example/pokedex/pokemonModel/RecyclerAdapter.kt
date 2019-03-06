package com.example.pokedex.pokemonModel

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pokedex.PokemonDataActivity
import com.example.pokedex.R
import kotlinx.android.synthetic.main.list_item.view.*


class RecyclerAdapter (private val pokemonList : ArrayList<Pokemon>) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    /*interface Listener {
        fun onItemClick(pokemon : Pokemon)

    }*/

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currPokemon = pokemonList.get(position)
        holder.bind(pokemonList[position])
        holder.currPokemon = currPokemon
    }

//Check how many items you have to display//

    override fun getItemCount(): Int = pokemonList.count()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)

    }

    class ViewHolder(view : View, var currPokemon: Pokemon? = null) : RecyclerView.ViewHolder(view) {

        companion object {
            val POKE_DATA_TITLE_KEY = "POKE NAME"
            val POKE_DATA_NAME = "POKE_NAME"
        }

        init {
            view.setOnClickListener{
                val intent = Intent(view.context, PokemonDataActivity::class.java)
                intent.putExtra(POKE_DATA_TITLE_KEY, currPokemon?.name)
                intent.putExtra(POKE_DATA_NAME, currPokemon?.name)
                view.context.startActivity(intent)
            }
        }
        fun bind(pokemon: Pokemon ) {
            itemView.pokemon_name.text = pokemon.name
            itemView.pokemon_url.text = pokemon.url

        }

    }
}