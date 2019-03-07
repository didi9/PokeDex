package com.example.pokedex
import com.example.pokedex.pokemonModel.AllPokemonResponse
import com.example.pokedex.pokemonModel.PokemonData
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonService {
    @GET("pokemon/")
    fun getAllPokemon() : Call<AllPokemonResponse>

    @GET("pokemon/{pokemonName}/")
    fun getPokemonByName(@Path("pokemonName") pokemonName: String): Call<PokemonData>



    companion object Factory {
        fun create(): PokemonService {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
            return retrofit.create(PokemonService::class.java)
        }

    }
}