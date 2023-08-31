package com.ccc.remind.presentation.ui.component.pageComponent.mindPost

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ccc.remind.R
import com.ccc.remind.presentation.ui.theme.RemindMaterialTheme

@Preview
@Composable
fun UploadPhotoCardPreview(
) {
    UploadPhotoCard(
        modifier = Modifier.size(100.dp)
    )
}


@Composable
fun UploadPhotoCard(
    modifier: Modifier = Modifier,
    maxSize: Int = 0,
    currentCount: Int = 0
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.border(
            width = 1.dp,
            color = RemindMaterialTheme.colorScheme.fg_subtle,
            shape = RoundedCornerShape(12.dp)
        ).then(modifier)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_camera),
            contentDescription = stringResource(R.string.camera),
            tint = RemindMaterialTheme.colorScheme.fg_subtle,
            modifier = Modifier.size(24.dp)
        )

        Text(
            text = "$currentCount / $maxSize",
            style = RemindMaterialTheme.typography.regular_md
        )
    }
}