package com.example.coininfo.data

import retrofit2.Response
import retrofit2.http.GET

interface CoinRepository {

    companion object {
        val instance: CoinRepository = RetrofitClient.getClient().create(CoinRepository::class.java)
    }

    @GET("coins")
    suspend fun getCoins(): Response<List<Coin>>
}
