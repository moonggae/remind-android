package com.ccc.remind.presentation.ui.component.pageComponent.mindPost

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.ccc.remind.presentation.ui.theme.RemindMaterialTheme

@Composable
fun UploadPhotoButton(
    modifier: Modifier = Modifier,
    onResult: (List<Uri>) -> Unit,
    maxItems: Int,
    content: @Composable () -> Unit
) {
    val pickMediaLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickMultipleVisualMedia(maxItems),
            onResult = onResult
        )

    Surface(
        onClick = {
            pickMediaLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        },
        modifier = modifier.semantics { role = Role.Button },
        shape = RoundedCornerShape(12.dp),
        color = RemindMaterialTheme.colorScheme.bg_default,
        content = content
    )
}