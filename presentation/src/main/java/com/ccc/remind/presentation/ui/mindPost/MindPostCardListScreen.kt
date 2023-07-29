package com.ccc.remind.presentation.ui.mindPost

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ccc.remind.R
import com.ccc.remind.presentation.ui.component.button.PrimaryButton
import com.ccc.remind.presentation.ui.component.mindPost.MindCardGrid
import com.ccc.remind.presentation.ui.component.mindPost.MindCardListFilterBar
import com.ccc.remind.presentation.ui.component.mindPost.StepBar
import com.ccc.remind.presentation.ui.component.model.MindFilter
import com.ccc.remind.presentation.ui.navigation.Route
import com.ccc.remind.presentation.ui.theme.RemindMaterialTheme

private const val TAG = "MindPostCardListScreen"

@Preview
@Composable
fun MindPostCardListScreenPreview() {
    MindPostCardListScreen(navController = rememberNavController())
}

@Composable
fun MindPostCardListScreen(
    navController: NavController,
    viewModel: MindPostViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier.padding(
            start = 20.dp,
            end = 20.dp,
            top = 32.dp
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(
                modifier = Modifier
                    .width(24.dp)
                    .height(24.dp),
                onClick = { /*TODO*/ }) {
                Icon(
                    painterResource(id = R.drawable.ic_x),
                    contentDescription = stringResource(R.string.exit)
                )
            }
        }
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

        MindCardListFilterBar(
            filterValues = MindFilter.values().toList(),
            selectedFilters = uiState.selectedMindFilters,
            onClickFilter = viewModel::updateMindCardFilter,
            onClickRefresh = viewModel::removeAllSelectedMindCards
        )

        Spacer(modifier = Modifier.height(28.dp))

        MindCardGrid(
            mindCards = uiState.filteredMindCards,
            selectedMindCards = uiState.selectedMindCards,
            onClickMindCard = viewModel::updateMindCard
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(28.dp),
        verticalArrangement = Arrangement.Bottom
    ) {
        PrimaryButton(
            onClick = {
                navController.navigate(Route.MindPost.Edit.name)
            },
            text = stringResource(R.string.to_next),
            enabled = uiState.enabledCardListSubmit
        )
    }
}