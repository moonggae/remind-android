package com.ccc.remind.presentation.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ccc.remind.presentation.ui.App
import com.ccc.remind.presentation.util.initCoil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initCoil(this)
        setContent {
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            App(
                MainUIState = uiState
            )
        }
    }
}

