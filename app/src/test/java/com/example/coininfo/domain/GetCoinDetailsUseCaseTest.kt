package com.example.coininfo.domain

import com.example.coininfo.data.Coin
import com.example.coininfo.data.CoinRepository
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class GetCoinDetailsUseCaseTest {

    private lateinit var getCoinDetailsUseCase: GetCoinDetailsUseCase
    private lateinit var repository: CoinRepository


    @BeforeEach
    fun setup() {
        repository = mockk()
        getCoinDetailsUseCase = GetCoinDetailsUseCase(repository)
    }

    @Test
    fun `given the repo returns Response where body is null when invoked then return null`() {
        coEvery {
            repository.getCoin("btc-bitcoin")
        } returns mockk<Response<Coin>>().apply {
            every {
                this@apply.body()
            } returns null
        }

        runTest {
            assertEquals(null, getCoinDetailsUseCase.execute("btc-bitcoin"))
        }
    }

    @Test
    fun `given the repo returns a response when invoked then return the body`() {
        val expectedCoin: Coin = mockk()
        coEvery {
            repository.getCoin("btc-bitcoin")
        } returns mockk<Response<Coin>>().apply {
            every {
                this@apply.body()
            } returns expectedCoin
        }

        runTest {
            assertEquals(expectedCoin, getCoinDetailsUseCase.execute("btc-bitcoin"))
        }
    }
}
