package com.example.coininfo.domain

import com.example.coininfo.data.CoinRepository
import com.example.coininfo.data.Tag
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext

class GetTagsUseCase(
    private val repository: CoinRepository = CoinRepository.instance
) {
    suspend fun execute(): List<Tag>? = coroutineScope {
        withContext(Dispatchers.IO) {
            repository.getTags().body()
        }
    }
}