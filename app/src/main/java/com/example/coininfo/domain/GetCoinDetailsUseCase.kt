package com.example.coininfo.domain

import com.example.coininfo.data.Coin
import com.example.coininfo.data.CoinRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext

class GetCoinDetailsUseCase(
    private val repository: CoinRepository = CoinRepository.instance
) {
    suspend fun execute(id: String): Coin? = coroutineScope {
        withContext(Dispatchers.IO) {
            repository.getCoin(id).body()
        }
    }
}
