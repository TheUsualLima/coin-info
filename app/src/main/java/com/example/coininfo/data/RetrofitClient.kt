package com.example.coininfo.data

import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

object RetrofitClient {

    private const val URL = "https://api.coinpaprika.com/v1/"

    fun getClient(): Retrofit = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(JacksonConverterFactory.create())
        .build()
}
