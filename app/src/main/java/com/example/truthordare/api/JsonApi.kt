package com.example.truthordare.api

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

const val BASE_URL = "https://jsonplaceholder.typicode.com/"

interface JsonApi {

    @GET("comments")
    fun getData() : Call<List<JsonModel>>

    companion object {
        operator fun invoke(): JsonApi {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(JsonApi::class.java)
        }
    }
}