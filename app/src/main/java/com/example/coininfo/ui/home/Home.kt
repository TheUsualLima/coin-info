@file:OptIn(ExperimentalFoundationApi::class)

package com.example.coininfo.ui.home

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.coininfo.R
import com.example.coininfo.ui.composables.BaseScaffold
import com.example.coininfo.ui.home.composables.CoinItem

@Composable
fun Home(
    state: State<HomeState>,
    shuffle: () -> Unit
) {
    BaseScaffold(title = stringResource(R.string.home_title)) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (state.value.error) {
                Text(text = "There was an error")
            } else {
                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    itemsIndexed(
                        items = state.value.coinData,
                        key = { _, coin ->
                            coin.id
                        }
                    ) { index, coin ->
                        CoinItem(
                            coin = coin,
                            modifier = Modifier
                                .animateItemPlacement(animationSpec = tween(500))
                                .clickable { shuffle() }
                                .padding(vertical = 8.dp)
                        )
                        if (index < state.value.coinData.lastIndex)
                            Divider(modifier = Modifier.padding(horizontal = 24.dp))
                    }
                }
            }
        }
    }
}
