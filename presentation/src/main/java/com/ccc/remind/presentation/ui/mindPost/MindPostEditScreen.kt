package com.ccc.remind.presentation.ui.mindPost

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun MindPostEditScreen(
    navController: NavController,
    viewModel: MindPostViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    Text(text = "SelectedMindCardSize: ${uiState.selectedMindCards.size}")
}