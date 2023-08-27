package com.example.coininfo.ui.home.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coininfo.R
import com.example.coininfo.data.Coin

@Composable
fun CoinItem(coin: Coin, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .height(IntrinsicSize.Max)
            .fillMaxWidth()
            .padding(end = 8.dp)
    ) {
        Text(
            text = coin.rank.toString(),
            modifier = Modifier.padding(horizontal = 8.dp),
            fontSize = 24.sp
        )
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = coin.name,
                modifier = Modifier.padding(vertical = 2.dp),
                fontSize = 18.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = coin.id,
                fontStyle = FontStyle.Italic,
                color = Color.DarkGray,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = coin.symbol,
                color = Color.DarkGray,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Column(
            modifier = Modifier
                .padding(horizontal = 4.dp)
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = if (coin.isActive) R.string.coin_item_active else R.string.coin_item_inactive),
                color = Color.DarkGray,
            )
            Text(text = coin.type)
        }
    }
}

@Preview()
@Composable
fun previewCoin() {
    Surface(modifier = Modifier.background(Color.White)) {
        CoinItem(
            Coin(
                id = "btc-bitcoin",
                name = "Bitcoin",
                symbol = "BTC",
                rank = 1,
                isNew = false,
                isActive = true,
                type = "coin"
            )
        )
    }
}
