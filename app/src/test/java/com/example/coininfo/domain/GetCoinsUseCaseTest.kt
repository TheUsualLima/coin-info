package com.example.coininfo.domain

import com.example.coininfo.data.Coin
import com.example.coininfo.data.CoinRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class GetCoinsUseCaseTest {

    private lateinit var getCoinsUseCase: GetCoinsUseCase
    private lateinit var repository: CoinRepository

    @BeforeEach
    fun setup() {
        repository = mockk()
        getCoinsUseCase = GetCoinsUseCase(repository)
    }

    @Test
    fun `given the repo returns Response where body is null when invoked then return null`() {
        coEvery {
            repository.getCoins()
        } returns mockk<Response<List<Coin>>>().apply {
            every {
                this@apply.body()
            } returns null
        }

        runTest {
            assertEquals(null, getCoinsUseCase.execute())
        }
    }

    @Test
    fun `given the repo returns a response when invoked then return the body`() {
        val expectedList: List<Coin> = listOf(mockk())
        coEvery {
            repository.getCoins()
        } returns mockk<Response<List<Coin>>>().apply {
            every {
                this@apply.body()
            } returns expectedList
        }

        runTest {
            assertEquals(expectedList, getCoinsUseCase.execute())
        }
    }
}
