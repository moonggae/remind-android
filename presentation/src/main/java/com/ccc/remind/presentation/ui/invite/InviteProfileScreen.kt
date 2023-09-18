package com.ccc.remind.presentation.ui.invite

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.transform.CircleCropTransformation
import com.ccc.remind.R
import com.ccc.remind.presentation.ui.component.button.PrimaryButton
import com.ccc.remind.presentation.ui.component.container.BasicScreen
import com.ccc.remind.presentation.ui.component.layout.AppBar
import com.ccc.remind.presentation.ui.navigation.Route
import com.ccc.remind.presentation.ui.theme.RemindMaterialTheme
import com.ccc.remind.presentation.util.buildCoilRequest

@Composable
@Preview(showBackground = true)
fun InviteProfileScreenPreview() {
    InviteProfileScreen()
}

@Composable
fun InviteProfileScreen(
    navController: NavController = rememberNavController(),
    viewModel: InviteViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(navController.currentDestination) {
        if(navController.currentDestination?.route != Route.Invite.Profile.name) {
            viewModel.removeOpenedProfile()
        }
    }
    
    BasicScreen(
        appBar = {
            AppBar(
                title = stringResource(R.string.invite_profile_appbar_title),
                navController = navController
            )
        },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))

        if(uiState.openedUserProfile?.profileImage == null) {
            Icon(
                painter = painterResource(id = R.drawable.ic_user),
                contentDescription = stringResource(R.string.user_icon),
                tint = RemindMaterialTheme.colorScheme.bg_muted,
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape)
                    .background(RemindMaterialTheme.colorScheme.fg_subtle)
            )
        } else {
            AsyncImage(
                model = buildCoilRequest(LocalContext.current, uiState.openedUserProfile?.profileImage?.url ?: "", listOf(CircleCropTransformation())),
                contentDescription = stringResource(R.string.user_profile_image),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(200.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = uiState.openedUserProfile?.displayName ?: "",
            style = RemindMaterialTheme.typography.bold_xxl,
            color = RemindMaterialTheme.colorScheme.fg_default
        )

        Spacer(modifier = Modifier.weight(1f))

        PrimaryButton(text = stringResource(id = R.string.invite_profile_invite_button)) {
            
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}