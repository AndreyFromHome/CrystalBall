package com.example.crystalball.model.apis

import com.example.crystalball.data.DictionaryItem
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {
    @GET("api/v2/entries/en/{getWord}")
    fun getDictionaryItem(@Path("getWord") getWord: String) : Call<List<DictionaryItem>>

    companion object {

        var BASE_URL = "https://api.dictionaryapi.dev/"

        fun create() : ApiInterface {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiInterface::class.java)

        }
    }
}