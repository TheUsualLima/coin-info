package com.example.coininfo.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CoinRepository {

    companion object {
        val instance: CoinRepository = RetrofitClient.getClient().create(CoinRepository::class.java)
    }

    @GET("coins")
    suspend fun getCoins(): Response<List<Coin>>

    @GET("coins/{id}")
    suspend fun getCoin(@Path("id") id: String): Response<Coin>

    @GET("tags")
    suspend fun getTags(): Response<List<Tag>>
}
