package com.example.coininfo.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.coininfo.data.Coin
import com.example.coininfo.domain.GetCoinsUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getCoinsUseCase: GetCoinsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    init {
        loadCoinData()
    }

    fun loadCoinData() {
        viewModelScope.launch {
            val coinResponse = getCoinsUseCase.execute()
            if (coinResponse != null) {
                _state.update { it.copy(coinData = coinResponse, error = false) }
                shuffle()
            } else {
                _state.update { it.copy(error = true) }
            }
        }
    }

    fun shuffle() {
        viewModelScope.launch {
            val list = mutableListOf<Coin>()
            val testItems = mutableListOf(
                Coin(
                    id = "btc-1",
                    name = "Bitcoin",
                    symbol = "BTC",
                    rank = 1,
                    isNew = false,
                    isActive = true,
                    type = "coin"
                ),
                Coin(
                    id = "2",
                    name = "Bitcoin",
                    symbol = "BTC",
                    rank = 2,
                    isNew = false,
                    isActive = true,
                    type = "coin"
                ),
                Coin(
                    id = "3",
                    name = "Bitcoin",
                    symbol = "BTC",
                    rank = 3,
                    isNew = false,
                    isActive = true,
                    type = "coin"
                )
            )
            list.add(testItems.removeAt((0..testItems.lastIndex).random()))
            list.add(testItems.removeAt((0..testItems.lastIndex).random()))
            list.add(testItems.removeAt((0..testItems.lastIndex).random()))
            _state.update { it.copy(coinData = list) }
        }
    }

    class Factory(
        private val getCoinsUseCase: GetCoinsUseCase
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
                return HomeViewModel(getCoinsUseCase) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}

data class HomeState(
    val error: Boolean = false,
    val coinData: List<Coin> = emptyList()
)
