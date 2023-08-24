package com.example.coininfo.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.coininfo.ui.home.Home

@Composable
fun CoinInfoApp(
    appState: CoinInfoAppState = rememberCoinInfoAppState()
) {
    NavHost(
        navController = appState.navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            Home()
        }
    }
}