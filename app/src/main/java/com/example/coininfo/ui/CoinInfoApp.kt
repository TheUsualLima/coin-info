package com.example.coininfo.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.coininfo.domain.GetCoinDetailsUseCase
import com.example.coininfo.domain.GetCoinsUseCase
import com.example.coininfo.domain.GetTagsUseCase
import com.example.coininfo.ui.home.Home
import com.example.coininfo.ui.home.HomeViewModel

@Composable
fun CoinInfoApp(
    appState: CoinInfoAppState = rememberCoinInfoAppState()
) {
    NavHost(
        navController = appState.navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            val homeViewModel = viewModel<HomeViewModel>(
                factory = HomeViewModel.Factory(
                    getCoinsUseCase = GetCoinsUseCase(),
                    getCoinDetailsUseCase = GetCoinDetailsUseCase(),
                    getTagsUseCase = GetTagsUseCase()
                )
            )
            Home(
                state = homeViewModel.state.collectAsState(),
                loadData = homeViewModel::loadData,
                loadCoin = homeViewModel::loadCoin,
                dismissCoinDialog = homeViewModel::hideCoin,
                selectTag = homeViewModel::selectTag
            )
        }
    }
}