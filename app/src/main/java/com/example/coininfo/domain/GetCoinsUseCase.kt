package com.example.coininfo.domain

import com.example.coininfo.data.Coin
import com.example.coininfo.data.CoinRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext

class GetCoinsUseCase(
    private val repository: CoinRepository = CoinRepository.instance
) {
    suspend fun execute(): List<Coin>? = coroutineScope {
        withContext(Dispatchers.IO) {
            repository.getCoins().body()
        }
    }
}
