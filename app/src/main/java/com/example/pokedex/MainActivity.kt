package com.example.pokedex

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import com.example.pokedex.pokemonModel.AllPokemonResponse
import com.example.pokedex.pokemonModel.Pokemon
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initRecyclerView()
        loadData()
        Toast.makeText(this@MainActivity, getString(R.string.instructions_main_toast), Toast.LENGTH_LONG).show()

    }
    private fun initRecyclerView() {
        val layoutManager : LayoutManager = LinearLayoutManager(this)
        pokemon_list.layoutManager = layoutManager

    }

    private fun loadData() {
        val pokemonService = PokemonService.create()
        val call = pokemonService.getAllPokemon()
        Log.d("CALL URL", call.request().url().toString())

        call.enqueue(object : Callback<AllPokemonResponse> {
            override fun onResponse(call: Call<AllPokemonResponse>, response: Response<AllPokemonResponse>) {
                handleResponse(response.body()?.results)

            }

            override fun onFailure(call: Call<AllPokemonResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, getString(R.string.generic_error_message), Toast.LENGTH_SHORT).show()
            }
        })

    }
    private fun handleResponse(pokemonList: List<Pokemon>?) {
        pokemon_list.layoutManager = LinearLayoutManager(this)
        val itemDecor = DividerItemDecoration(this, VERTICAL)
        pokemon_list.addItemDecoration(itemDecor)
        pokemonArrayList = ArrayList(pokemonList)
        recyclerAdapter = RecyclerAdapter(pokemonArrayList!!)
        pokemon_list.adapter = recyclerAdapter

    }

}
