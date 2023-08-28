package com.example.coininfo.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.coininfo.data.Coin
import com.example.coininfo.domain.GetCoinDetailsUseCase
import com.example.coininfo.domain.GetCoinsUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(
    private val getCoinsUseCase: GetCoinsUseCase,
    private val getCoinDetailsUseCase: GetCoinDetailsUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    init {
        loadCoinData()
//        _state.update { it.copy(error = true) } // For testing the error state
    }

    fun loadCoinData() {
        viewModelScope.launch {
            startLoading()
            val coinResponse = getCoinsUseCase.execute()
            if (coinResponse != null) {
                _state.update {
                    it.copy(
                        coinListData = sortByName(coinResponse),
                        error = false
                    )
                }
            } else {
                _state.update { it.copy(error = true) }
            }
            stopLoading()
        }
    }

    fun loadCoin(id: String) {
        viewModelScope.launch {
            startLoading()
            val coinDetailResponse = getCoinDetailsUseCase.execute(id)
            if (coinDetailResponse != null) {
                _state.update {
                    it.copy(
                        showCoin = true,
                        coinData = coinDetailResponse
                    )
                }
            }
            stopLoading()
        }
    }

    fun hideCoin() {
        _state.update { it.copy(showCoin = false) }
    }

    private suspend fun sortByName(coinList: List<Coin>): List<Coin> = withContext(dispatcher) {
        coinList.sortedBy { it.name }
    }

    private fun startLoading() {
        _state.update { it.copy(isLoading = true) }
    }

    private fun stopLoading() {
        _state.update { it.copy(isLoading = false) }
    }

    class Factory(
        private val getCoinsUseCase: GetCoinsUseCase,
        private val getCoinDetailsUseCase: GetCoinDetailsUseCase
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
                return HomeViewModel(getCoinsUseCase, getCoinDetailsUseCase) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}

data class HomeState(
    val error: Boolean = false,
    val isLoading: Boolean = false,
    val coinListData: List<Coin> = emptyList(),
    val showCoin: Boolean = false,
    val coinData: Coin? = null,
)
