package com.ccc.remind.presentation.ui.component.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.ccc.remind.presentation.ui.theme.RemindMaterialTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModalBottomSheet(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    shape: Shape = RoundedCornerShape(8.dp),
    sheetState: SheetState,
    padding: PaddingValues = PaddingValues(30.dp),
    content: @Composable() (ColumnScope.() -> Unit),
) {
    androidx.compose.material3.ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        shape = shape,
        dragHandle = null,
        containerColor = Color.Transparent,
    ) {
        Column(
            modifier = Modifier
                .padding(padding)
                .clip(shape)
                .background(color = RemindMaterialTheme.colorScheme.bg_default)
            ,
            content = content
        )
    }
}