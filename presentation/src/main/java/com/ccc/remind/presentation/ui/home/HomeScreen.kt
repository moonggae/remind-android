package com.ccc.remind.presentation.ui.home

import android.content.Intent
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ccc.remind.R
import com.ccc.remind.presentation.ui.component.container.BackgroundContainer
import com.ccc.remind.presentation.ui.navigation.Route
import com.ccc.remind.presentation.ui.theme.RemindMaterialTheme


@Composable
fun HomeScreen(
    navController: NavController
) {
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(5)) { uris ->
        if (uris.isNotEmpty()) {
            Log.d("PhotoPicker", "Number of items selected: ${uris.size}")
            uris.forEach {  uri ->
                Log.d("PhotoPicker", "uri: $uri")
            }
        } else {
            Log.d("PhotoPicker", "No media selected")
        }
    }

    val takePhotoFromAlbumIntent =
        Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).apply {
            type = "image/*"
            action = Intent.ACTION_GET_CONTENT
            putExtra(
                Intent.EXTRA_MIME_TYPES,
                arrayOf("image/jpeg", "image/png", "image/bmp", "image/webp")
            )
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false)
        }

    Column(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .background(color = RemindMaterialTheme.colorScheme.bg_default)
    ) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween

        ) {
            Text(
                stringResource(id = R.string.home_title_greeting, "유저"),
                style = RemindMaterialTheme.typography.bold_xxl,
                modifier = Modifier.padding(top = 40.dp)
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
                verticalAlignment = Alignment.Top,
                modifier = Modifier.padding(top = 28.dp)
            ) {
                IconButton(onClick = {
                    launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                }, modifier = Modifier.size(40.dp)) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_notification_off),
                        contentDescription = null
                    )
                }

                IconButton(
                    onClick = {
                        navController.navigate(Route.MindPost.Edit.name)
                    },
                    modifier = Modifier.size(40.dp))
                {
                    Image(
                        painter = painterResource(id = R.drawable.ic_calendar),
                        contentDescription = null
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(25.dp))

        BackgroundContainer {
            Column(
                modifier = Modifier.padding(vertical = 68.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.home_empty_mind_box_message, "유저"),
                    style = RemindMaterialTheme.typography.bold_lg,
                    color = RemindMaterialTheme.colorScheme.fg_muted,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 20.dp)
                )
                IconButton(
                    modifier = Modifier
                        .size(40.dp)
                        .background(color = RemindMaterialTheme.colorScheme.fg_subtle, shape = CircleShape),
                    onClick = { /*TODO*/ }
                ) {
                    Image(painter = painterResource(id = R.drawable.ic_plus), contentDescription = null)
                }
            }
        }

        Spacer(modifier = Modifier.height(26.dp))

        Text(
            text = stringResource(id = R.string.home_label_mind_history),
            style = RemindMaterialTheme.typography.bold_lg,
            color = RemindMaterialTheme.colorScheme.fg_default
        )
        Spacer(modifier = Modifier.height(8.dp))
        BackgroundContainer(modifier = Modifier.padding(vertical = 32.dp)) {
            Text(text = stringResource(id = R.string.home_empty_mind_history), style = RemindMaterialTheme.typography.regular_lg)
        }

        Spacer(modifier = Modifier.height(26.dp))

        Text(
            text = stringResource(id = R.string.home_label_mind_memo),
            style = RemindMaterialTheme.typography.bold_lg,
            color = RemindMaterialTheme.colorScheme.fg_default
        )
        Spacer(modifier = Modifier.height(8.dp))
        BackgroundContainer(modifier = Modifier.padding(vertical = 32.dp)) {
            Text(text = stringResource(id = R.string.home_empty_mind_memo), style = RemindMaterialTheme.typography.regular_lg)

        }
    }
}