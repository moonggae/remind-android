package com.ccc.remind.presentation.ui.component.container

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BasicScreen(
    modifier: Modifier = Modifier,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    appBar: @Composable (() -> Unit)? = null,
    padding: PaddingValues = PaddingValues(horizontal = 20.dp),
    content: @Composable (ColumnScope.() -> Unit)
) {
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