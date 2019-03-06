package com.example.pokedex

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import com.example.pokedex.pokemonModel.AllPokemonResponse
import com.example.pokedex.pokemonModel.Pokemon
import com.example.pokedex.pokemonModel.RecyclerAdapter
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import java.util.*
import retrofit2.Response
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.RecyclerView.*


class MainActivity : AppCompatActivity() {

    private var recyclerAdapter: RecyclerAdapter? = null
    private var pokemonArrayList: ArrayList<Pokemon>? = null
    private val BASE_URL = "https://pokeapi.co/api/v2/"

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("BEGIN", "STARTED")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initRecyclerView()
        loadData()

    }
    private fun initRecyclerView() {

        val layoutManager : LayoutManager = LinearLayoutManager(this)
        pokemon_list.layoutManager = layoutManager

    }

    private fun loadData() {
        val pokemonService = PokemonService.create(BASE_URL)
        val call = pokemonService.getAllPokemon()
        Log.d("CALL URL", call.request().url().toString())

        call.enqueue(object : Callback<AllPokemonResponse> {
            override fun onResponse(call: Call<AllPokemonResponse>, response: Response<AllPokemonResponse>) {
                handleResponse(response.body()?.results)

            }

            override fun onFailure(call: Call<AllPokemonResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show()
            }
        })

    }
    private fun handleResponse(pokemonList: List<Pokemon>?) {
        Log.d("RESULT", pokemonList!![2].name)

        pokemon_list.layoutManager = LinearLayoutManager(this)
        val itemDecor = DividerItemDecoration(this, VERTICAL)
        pokemon_list.addItemDecoration(itemDecor)
        pokemonArrayList = ArrayList(pokemonList)
        recyclerAdapter = RecyclerAdapter(pokemonArrayList!!)
        pokemon_list.adapter = recyclerAdapter

    }

}
