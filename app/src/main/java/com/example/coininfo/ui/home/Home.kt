@file:OptIn(ExperimentalFoundationApi::class)

package com.example.coininfo.ui.home

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.coininfo.R
import com.example.coininfo.data.Coin
import com.example.coininfo.ui.composables.BaseScaffold
import com.example.coininfo.ui.composables.ErrorPrompt
import com.example.coininfo.ui.composables.LoadingSpinner
import com.example.coininfo.ui.home.composables.CoinItem
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun Home(
    state: State<HomeState>,
    loadData: () -> Unit,
    loadCoin: (id: Coin) -> Unit,
    dismissCoinDialog: () -> Unit
) {
    val pullRefreshState = rememberPullRefreshState(refreshing = false, onRefresh = loadData)
    val listState = rememberLazyListState()
    val showFab by remember { derivedStateOf { listState.firstVisibleItemIndex > 0 } }
    val coroutineScope = rememberCoroutineScope()

    BaseScaffold(
        title = stringResource(R.string.home_title),
        showFab = showFab,
        fabAction = {
            coroutineScope.launch {
                listState.animateScrollToItem(0)
            }
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (state.value.error) {
                ErrorPrompt(
                    errorMessage = stringResource(R.string.default_error_text),
                    buttonText = stringResource(R.string.reload_button),
                    buttonOnClick = loadData,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .pullRefresh(pullRefreshState)
                ) {
                    LazyColumn(modifier = Modifier.fillMaxWidth(), state = listState) {
                        itemsIndexed(
                            items = state.value.coinListData,
                            key = { _, coin ->
                                coin.id
                            }
                        ) { index, coin ->
                            CoinItem(
                                coin = coin,
                                modifier = Modifier
                                    .animateItemPlacement(animationSpec = tween(500))
                                    .clickable { loadCoin(coin) }
                                    .padding(vertical = 8.dp)
                            )
                            if (index < state.value.coinListData.lastIndex) Divider(modifier = Modifier.padding(horizontal = 24.dp))
                        }
                    }
                    PullRefreshIndicator(
                        refreshing = state.value.isLoading,
                        state = pullRefreshState,
                        modifier = Modifier.align(Alignment.TopCenter)
                    )
                    if (state.value.showCoin) {
                        AlertDialog(onDismissRequest = dismissCoinDialog) {
                            state.value.coinData?.let { coin ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentHeight()
                                        .padding(vertical = 16.dp),
                                    shape = RoundedCornerShape(12.dp),
                                ) {
                                    Column(modifier = Modifier.padding(horizontal = 18.dp, vertical = 14.dp)) {
                                        Text(coin.name)
                                        Text(coin.symbol)
                                        Text(coin.id)
                                        Text(coin.type)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (state.value.isLoading) LoadingSpinner(modifier = Modifier.fillMaxSize())
    }
}
