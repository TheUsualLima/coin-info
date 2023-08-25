package com.example.coininfo.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.coininfo.data.Coin
import com.example.coininfo.domain.GetCoinsUseCase
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
        viewModelScope.launch(Dispatchers.IO) {
            val coinResponse = getCoinsUseCase()
            if (coinResponse != null) {
                _state.update { it.copy(coinData = coinResponse) }
            } else {
                _state.update { it.copy(error = true) }
            }
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
