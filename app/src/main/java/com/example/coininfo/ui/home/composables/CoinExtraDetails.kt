package com.example.coininfo.ui.home.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.coininfo.R
import com.example.coininfo.data.Coin

@Composable
fun CoinExtraDetails(coin: Coin) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(vertical = 16.dp),
        shape = RoundedCornerShape(12.dp),
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 18.dp, vertical = 14.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(stringResource(R.string.coin_name_template, coin.name))
            Text(stringResource(R.string.coin_id_template, coin.id))
            coin.description?.let { Text(stringResource(R.string.coin_description_template, it)) }
            coin.message.takeIf { !it.isNullOrEmpty() }?.let { Text(stringResource(R.string.coin_message_template, it)) }
            Text(stringResource(R.string.coin_symbol_template, coin.symbol))
            Text(stringResource(R.string.coin_type_template, coin.type))
            coin.dateStarted?.let { Text(stringResource(R.string.coin_date_started_template, it)) }
            coin.team.takeIf { !it.isNullOrEmpty() }?.let { team ->
                val members = team.joinToString(", ") { it.name }
                Text(stringResource(R.string.coin_team_members_template, members))
            }
            coin.isOpenSource?.let { Text(stringResource(R.string.coin_is_open_source_template, it)) }
            coin.developmentStatus?.let { Text(stringResource(R.string.coin_dev_status_template, it))}
            coin.tags.takeIf { !it.isNullOrEmpty() }?.let { tags ->
                val members = tags.joinToString(", ") { it.name }
                Text(stringResource(R.string.coin_tags_template, members))
            }
        }
    }

}
