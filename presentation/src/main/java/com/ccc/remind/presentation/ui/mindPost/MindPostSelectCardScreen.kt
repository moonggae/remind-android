package com.ccc.remind.presentation.ui.mindPost

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ccc.remind.R
import com.ccc.remind.presentation.navigation.Route
import com.ccc.remind.presentation.ui.component.container.BasicScreen
import com.ccc.remind.presentation.ui.component.layout.AppBar
import com.ccc.remind.presentation.ui.component.layout.MindCardPostGrid
import com.ccc.remind.presentation.ui.component.pageComponent.mindPost.MindCardListFilterBar
import com.ccc.remind.presentation.ui.component.pageComponent.mindPost.StepBar
import com.ccc.remind.presentation.ui.theme.RemindMaterialTheme

private const val TAG = "TAG"

@Preview
@Composable
fun MindPostCardListScreenPreview() {
    MindPostSelectCardScreen(navController = rememberNavController())
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MindPostSelectCardScreen(
    navController: NavController,
    viewModel: MindPostViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val nextButton: @Composable (() -> Unit) = {
        CompositionLocalProvider(
            LocalMinimumInteractiveComponentEnforcement provides false
        ) {
            TextButton(
                onClick = { navController.navigate(Route.MindPost.Edit.name) },
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier.padding(0.dp),
                enabled = uiState.enabledCardListSubmit,
                colors = ButtonDefaults.buttonColors(
                    contentColor = RemindMaterialTheme.colorScheme.text_button_blue,
                    disabledContentColor = RemindMaterialTheme.colorScheme.accent_onAccent,
                    containerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent
                )
            ) {
                Text(
                    text = stringResource(id = R.string.to_next),
                    style = RemindMaterialTheme.typography.regular_xl
                )
            }
        }
    }

    BasicScreen(
        appBar = {
            AppBar(
                title = "",
                isExit = true,
                navController = navController,
                suffix = { nextButton() },
            )
        }
    ) {
        Text(
            text = "${stringResource(R.string.step)} 1 / 2",
            style = RemindMaterialTheme.typography.bold_md
        )

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = stringResource(R.string.mind_post_card_list_title),
            style = RemindMaterialTheme.typography.bold_xl
        )

        Spacer(modifier = Modifier.height(12.dp))

        StepBar(maxStep = 2, currentStep = 0)

        Spacer(modifier = Modifier.height(37.dp))

        Text(
            text = stringResource(R.string.mind_post_card_list_label),
            style = RemindMaterialTheme.typography.bold_xl
        )
        Spacer(modifier = Modifier.height(3.dp))
        Text(
            text = stringResource(R.string.mind_post_card_list_label_sub),
            style = RemindMaterialTheme.typography.regular_md
        )

        Spacer(modifier = Modifier.height(20.dp))

        MindCardListFilterBar( // todo : make to be clean with card list screen
            filterValues = uiState.mindFilters,
            selectedFilters = uiState.selectedMindFilters,
            onClickFilter = viewModel::updateMindCardFilter,
            onClickRefresh = viewModel::removeAllSelectedMindCards
        )

        Spacer(modifier = Modifier.height(28.dp))

        MindCardPostGrid(
            mindCards = uiState.filteredMindCards,
            selectedMindCards = uiState.selectedMindCards,
            onClickMindCard = viewModel::updateMindCard
        )

    }
}