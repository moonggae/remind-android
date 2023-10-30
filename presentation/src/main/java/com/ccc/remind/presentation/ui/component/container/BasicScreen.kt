package com.ccc.remind.presentation.ui.component.container

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.ccc.remind.presentation.base.ComposeLifecycleViewModel

@Composable
fun BasicScreen(
    viewModel: ComposeLifecycleViewModel? = null,
    modifier: Modifier = Modifier,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    appBar: @Composable (() -> Unit)? = null,
    padding: PaddingValues = PaddingValues(horizontal = 20.dp),
    content: @Composable (ColumnScope.() -> Unit)
) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    DisposableEffect(lifecycle) {
        val lifecycleObserver = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_CREATE -> viewModel?.onCreate()
                Lifecycle.Event.ON_START -> viewModel?.onStart()
                Lifecycle.Event.ON_RESUME -> viewModel?.onResume()
                Lifecycle.Event.ON_PAUSE -> viewModel?.onPause()
                Lifecycle.Event.ON_STOP -> viewModel?.onStop()
                else -> {}
            }
        }

        lifecycle.addObserver(lifecycleObserver)
        onDispose {
            lifecycle.removeObserver(lifecycleObserver)
            viewModel?.onDispose()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        appBar?.invoke()

        Column(
            modifier.then(
                Modifier
                    .padding(padding)
                    .fillMaxSize()
            ),
            verticalArrangement,
            horizontalAlignment,
            content
        )
    }
}