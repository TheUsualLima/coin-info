package com.example.coininfo.ui

import com.example.coininfo.data.Coin
import com.example.coininfo.domain.GetCoinDetailsUseCase
import com.example.coininfo.domain.GetCoinsUseCase
import com.example.coininfo.ui.home.HomeViewModel
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private val dispatcher: TestDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: HomeViewModel
    private lateinit var getCoinsUseCase: GetCoinsUseCase
    private lateinit var getCoinDetailsUseCase: GetCoinDetailsUseCase

    @BeforeEach
    fun setup() {
        Dispatchers.setMain(dispatcher)
        getCoinsUseCase = mockk()
        getCoinDetailsUseCase = mockk()
        viewModel = HomeViewModel(getCoinsUseCase, getCoinDetailsUseCase, dispatcher = dispatcher)
    }

    @AfterEach
    fun after() {
        Dispatchers.resetMain()
    }

    @Test
    fun `given GetCoinsUseCase returns null when loading coin data then state error is true`() {
        coEvery {
            getCoinsUseCase.execute()
        } returns null

        runTest {
            viewModel.loadCoinData()
            advanceUntilIdle()
            assertTrue(viewModel.state.value.error)
        }
    }

    @Test
    fun `given GetCoinsUseCase returns a list when loading coin data then state error is false`() {
        coEvery {
            getCoinsUseCase.execute()
        } returns listOf(mockk())

        runTest {
            viewModel.loadCoinData()
            advanceUntilIdle()
            assertFalse(viewModel.state.value.error)
        }
    }

    @Test
    fun `given GetCoinsUseCase returns a list when loading coin data then state is updated with that list`() {
        val expectedCoinList: List<Coin> = listOf(mockk())
        coEvery {
            getCoinsUseCase.execute()
        } returns expectedCoinList

        runTest {
            viewModel.loadCoinData()
            advanceUntilIdle()
            assertEquals(expectedCoinList, viewModel.state.value.coinListData)
        }
    }

    @Test
    fun `given GetCoinsUseCase returns a list when loading coin data then data is sorted by name`() {
        val coin1: Coin = mockk()
        val coin2: Coin = mockk()
        val coin3: Coin = mockk()
        val coin4: Coin = mockk()
        val mockCoins: List<Coin> = listOf(coin1, coin2, coin3, coin4)
        val expectedList: List<Coin> = listOf(coin3, coin2, coin4, coin1)
        every { coin1.name } returns "Dogecoin"
        every { coin2.name } returns "Chainlink"
        every { coin3.name } returns "Bitcoin"
        every { coin4.name } returns "Chainlink"
        coEvery {
            getCoinsUseCase.execute()
        } returns mockCoins

        runTest {
            viewModel.loadCoinData()
            advanceUntilIdle()
            assertEquals(expectedList, viewModel.state.value.coinListData)
        }
    }

    @Test
    fun `given getCoinDetailsUseCase returns a Coin when loading coin extra details then update showCoin to true`() {
        coEvery {
            getCoinDetailsUseCase.execute("btc-bitcoin")
        } returns mockk()

        runTest {
            viewModel.loadCoin("btc-bitcoin")
            advanceUntilIdle()
            assertTrue(viewModel.state.value.showCoin)
        }
    }

    @Test
    fun `given getCoinDetailsUseCase returns a Coin when loading coin extra details then state is updated with that info`() {
        val expectedCoin = mockk<Coin>()
        coEvery {
            getCoinDetailsUseCase.execute("btc-bitcoin")
        } returns expectedCoin

        runTest {
            viewModel.loadCoin("btc-bitcoin")
            advanceUntilIdle()
            assertEquals(expectedCoin, viewModel.state.value.coinData)
        }
    }
}
