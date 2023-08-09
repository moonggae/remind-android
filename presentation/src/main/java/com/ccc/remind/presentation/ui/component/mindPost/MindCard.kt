package com.ccc.remind.presentation.ui.component.mindPost

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ccc.remind.domain.entity.mind.MindCard
import com.ccc.remind.domain.entity.mind.MindCardSelectType
import com.ccc.remind.presentation.ui.theme.RemindMaterialTheme

@Composable
fun MindCard(
    modifier: Modifier = Modifier,
    mindCard: MindCard,
    selectType: MindCardSelectType? = null,
    onClick: (MindCard) -> Unit = {}
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onClick(mindCard) }
    ) {
        MindCardImage(
            url = mindCard.imageUrl ?: "",
            selectType = selectType
        )
        if (selectType != null)
            Text(
                text = mindCard.displayName,
                style = RemindMaterialTheme.typography.bold_lg,
                color = RemindMaterialTheme.colorScheme.accent_default
            )
        else
            Text(
                text = mindCard.displayName,
                style = RemindMaterialTheme.typography.regular_lg
            )
    }
}