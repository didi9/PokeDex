package com.example.pokedex

import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.util.Log
import android.widget.Toast
import com.example.pokedex.pokemonModel.PokeTypeInfo
import com.example.pokedex.pokemonModel.PokemonData
import com.example.pokedex.pokemonModel.RecyclerAdapter.ViewHolder.Companion.POKE_DATA_NAME
import com.example.pokedex.pokemonModel.RecyclerAdapter.ViewHolder.Companion.POKE_DATA_TITLE_KEY
import kotlinx.android.synthetic.main.activity_pokemon_data.*
import kotlinx.android.synthetic.main.content_pokemon_data.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PokemonDataActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_data)
        setSupportActionBar(toolbar)

        val navBarTitle = intent.getStringExtra(POKE_DATA_TITLE_KEY).toUpperCase()
        supportActionBar?.title = navBarTitle

        loadData()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }


    private fun loadData() {
        val pokeDataName = intent.getStringExtra(POKE_DATA_NAME)
        val pokemonService = PokemonService.create("https://pokeapi.co/api/v2/")
        Log.d("Pok DATA URL", pokeDataName)

        val call = pokemonService.getPokemonByName(pokeDataName)
        Log.d("CALL DATA URL", call.request().url().toString())

        call.enqueue(object : Callback<PokemonData> {
            override fun onResponse(call: Call<PokemonData>, response: Response<PokemonData>) {
               // handleResponse(response.body()?.results, pokemonService)
                Log.d("WEIGHT_DATA", "type: " + response.body()?.types?.get(0)?.type?.name)

                pokemon_weight.text  = response.body()?.weight.toString()
                pokemon_height.text  = response.body()?.height.toString()
                val builder = StringBuilder()
               val typeArray = response.body()?.types
                for(i in typeArray.orEmpty()){
                    builder.append(i.type.name + "\n")
                }
                pokemon_types_text.text = builder.toString()
            }

            override fun onFailure(call: Call<PokemonData>, t: Throwable) {
                Toast.makeText(this@PokemonDataActivity, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show()
            }
        })
        }
    }


