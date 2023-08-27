package com.example.coininfo.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun LoadingSpinner(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        color = Color.Black.copy(alpha = 0.15f)
    ) {
        CircularProgressIndicator(modifier = Modifier.wrapContentSize())
    }
}

@Preview
@Composable
fun LoadingSpinnerPreview() {
    Box(modifier = Modifier.background(Color.Green).fillMaxSize()) {
        Text(text = "JASON")
        LoadingSpinner(modifier = Modifier.fillMaxSize())
    }
}
