package com.example.coininfo.ui.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.coininfo.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseScaffold(
    title: String,
    showFab: Boolean = false,
    fabAction: (() -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title, modifier = Modifier.fillMaxWidth()) },
            )
        },
        modifier = Modifier.fillMaxSize(),
        content = { padding ->
            Box(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxWidth()
            ) {
                content()
            }
        },
        floatingActionButton = {
            AnimatedVisibility(
                visible = showFab,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                FloatingActionButton(onClick = { fabAction?.invoke() }) {
                    Icon(Icons.Filled.KeyboardArrowUp, stringResource(R.string.fab_content_description))
                }
            }
        }
    )
}
