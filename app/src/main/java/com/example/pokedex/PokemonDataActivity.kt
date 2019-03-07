package com.example.pokedex

import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast
import com.example.pokedex.pokemonModel.PokemonData
import com.example.pokedex.RecyclerAdapter.ViewHolder.Companion.POKE_DATA_NAME
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
        val pokeDataName = intent.getStringExtra(POKE_DATA_NAME)
        val navBarTitle = pokeDataName.capitalize()
        supportActionBar?.title = navBarTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        loadData(pokeDataName)

    }


    private fun loadData(pokeDataName : String) {
        val pokemonService = PokemonService.create()
        val call = pokemonService.getPokemonByName(pokeDataName)

        call.enqueue(object : Callback<PokemonData> {
            override fun onResponse(call: Call<PokemonData>, response: Response<PokemonData>) {

                pokemon_weight.text  = response.body()?.weight.toString()
                pokemon_height.text  = response.body()?.height.toString()
                val builder = StringBuilder()
               val typeArray = response.body()?.types
                for(i in typeArray.orEmpty()){
                    builder.append(i.type.name + "\n")
                }
                pokemon_types_text.text = builder.toString().trim('\n')
            }

            override fun onFailure(call: Call<PokemonData>, t: Throwable) {
                Toast.makeText(this@PokemonDataActivity, getString(R.string.generic_error_message), Toast.LENGTH_SHORT).show()
            }
        })
        }
    }


