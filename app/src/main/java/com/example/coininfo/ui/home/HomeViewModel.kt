package com.example.coininfo.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.coininfo.data.Coin
import com.example.coininfo.data.Tag
import com.example.coininfo.domain.GetCoinDetailsUseCase
import com.example.coininfo.domain.GetCoinsUseCase
import com.example.coininfo.domain.GetTagsUseCase
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
    private val getTagsUseCase: GetTagsUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    private val tags = mutableListOf<Tag>()
    private var sortedCoinList = listOf<Coin>()

    init {
        loadData()
//        _state.update { it.copy(error = true) } // For testing the error state
    }

    fun loadData() {
        viewModelScope.launch(dispatcher) {
            startLoading()

            if (tags.isEmpty()) {
                loadTags()
            }

            val coinResponse = getCoinsUseCase.execute()
            if (coinResponse != null) {
                //Add fake tags
                if (tags.isNotEmpty()) {
                    val taggedCoins = mutableListOf<Coin>()
                    coinResponse.forEach { coin ->
                        taggedCoins.add(
                            coin.copy(tags = listOf(tags.random(), tags.random(), tags.random()))
                        )
                    }
                    sortedCoinList = sortByName(taggedCoins)
                } else {
                    sortedCoinList = sortByName(coinResponse)
                }

                _state.update {
                    it.copy(
                        coinListData = sortedCoinList,
                        error = false,
                        selectedTag = ""
                    )
                }
            } else {
                _state.update { it.copy(error = true) }
            }
            stopLoading()
        }
    }

    fun loadCoin(id: String) {
        viewModelScope.launch(dispatcher) {
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

    private fun loadTags() {
        viewModelScope.launch(dispatcher) {
            startLoading()
            val tagResponse = getTagsUseCase.execute()
            if (tagResponse != null) {
                val tagStrings = tagResponse.map(Tag::id)
                _state.update { it.copy(tagOptions = tagStrings) }
                tags.clear()
                tags.addAll(tagResponse)
            }
        }
    }

    fun hideCoin() {
        _state.update { it.copy(showCoin = false) }
    }

    fun selectTag(tagId: String) {
        viewModelScope.launch(dispatcher) {
            val filteredList = sortedCoinList.filter {
                it.tags?.any { tag -> tag.id == tagId } ?: false
            }
            _state.update { it.copy(selectedTag = tagId, coinListData = filteredList) }
        }
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
        private val getCoinDetailsUseCase: GetCoinDetailsUseCase,
        private val getTagsUseCase: GetTagsUseCase
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
                return HomeViewModel(getCoinsUseCase, getCoinDetailsUseCase, getTagsUseCase) as T
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
    val tagOptions: List<String> = emptyList(),
    val selectedTag: String = ""
)
