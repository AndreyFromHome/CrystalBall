package com.example.crystalball.model.apis

import com.example.crystalball.data.DictionaryItem
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiInterface {
    @GET("api/v2/entries/en/android")
    fun getDictionaryItem() : Call<List<DictionaryItem>>

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