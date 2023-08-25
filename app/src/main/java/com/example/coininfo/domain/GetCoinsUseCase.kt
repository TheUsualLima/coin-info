package com.example.coininfo.domain

import com.example.coininfo.data.Coin

class GetCoinsUseCase {
    suspend operator fun invoke(): List<Coin> = buildList {
        for (i in 0..100) {
            add(Coin("Bitcoin"))
            add(Coin("Ethereum"))
            add(Coin("Tether"))
            add(Coin("Cardano"))
            add(Coin("Dogecoin"))
        }
    }
}
