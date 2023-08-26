package com.example.coininfo.domain

import com.example.coininfo.data.Coin
import com.example.coininfo.data.CoinRepository
import kotlinx.coroutines.coroutineScope

class GetCoinsUseCase(
    private val repository: CoinRepository = CoinRepository.instance
) {
    suspend fun execute(): List<Coin>? = coroutineScope {
        repository.getCoins().body()
    }
}
