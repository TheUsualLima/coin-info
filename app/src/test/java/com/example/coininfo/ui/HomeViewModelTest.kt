package com.example.coininfo.ui

import com.example.coininfo.data.Coin
import com.example.coininfo.domain.GetCoinsUseCase
import com.example.coininfo.ui.home.HomeViewModel
import io.mockk.coEvery
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
    private lateinit var useCase: GetCoinsUseCase

    @BeforeEach
    fun setup() {
        Dispatchers.setMain(dispatcher)
        useCase = mockk()
        viewModel = HomeViewModel(useCase)
    }

    @AfterEach
    fun after() {
        Dispatchers.resetMain()
    }

    @Test
    fun `given usecase returns null when loading coin data then state error is true`() {
        coEvery {
            useCase.execute()
        } returns null

        runTest {
            viewModel.loadCoinData()
            advanceUntilIdle()
            assertTrue(viewModel.state.value.error)
        }
    }

    @Test
    fun `given usecase returns a list when loading coin data then state error is false`() {
        coEvery {
            useCase.execute()
        } returns listOf(mockk())

        runTest {
            viewModel.loadCoinData()
            advanceUntilIdle()
            assertFalse(viewModel.state.value.error)
        }
    }

    @Test
    fun `given usecase returns a list when loading coin data then state is updated with that list`() {
        val expectedCoinList: List<Coin> = listOf(mockk())
        coEvery {
            useCase.execute()
        } returns expectedCoinList

        runTest {
            viewModel.loadCoinData()
            advanceUntilIdle()
            assertEquals(expectedCoinList, viewModel.state.value.coinData)
        }
    }
}
