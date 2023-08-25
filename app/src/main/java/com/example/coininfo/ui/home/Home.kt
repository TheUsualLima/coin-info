package com.example.coininfo.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.coininfo.R
import com.example.coininfo.ui.composables.BaseScaffold

@Composable
fun Home(
    state: State<HomeState>
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
                    items(items = state.value.coinData) { coin ->
                        Text(text = coin.name)
                    }
                }
            }
        }
    }
}
