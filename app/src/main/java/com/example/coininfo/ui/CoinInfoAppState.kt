package com.example.coininfo.ui

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

sealed class Screen(val route: String) {
    object Home : Screen("home")
}

@Composable
fun rememberCoinInfoAppState(
    navController: NavHostController = rememberNavController(),
    context: Context = LocalContext.current
)  = remember(navController, context) {
    CoinInfoAppState(navController)
}

class CoinInfoAppState(
    val navController: NavHostController
)
