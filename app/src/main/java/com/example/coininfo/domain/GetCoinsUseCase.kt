package com.example.coininfo.domain

import com.example.coininfo.data.Coin
import com.example.coininfo.data.CoinRepository

class GetCoinsUseCase(private val repository: CoinRepository = CoinRepository.instance) {
    suspend operator fun invoke(): List<Coin>? = repository.getCoins().body()
}
