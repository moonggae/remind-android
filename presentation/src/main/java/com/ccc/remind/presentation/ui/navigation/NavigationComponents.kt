package com.ccc.remind.presentation.ui.navigation

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ccc.remind.R

@Composable
fun BottomNavigationBar(
    selectedDestination: String,
    navigateToTopLevelDestination: (TopLevelDestination) -> Unit
) {
    val selectedDestinationRoute = Route.fromName(selectedDestination)

    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(68.dp)
            .border(width = 1.dp, color = colorResource(id = R.color.fg_subtle)),
        containerColor = colorResource(id = R.color.bg_muted)
    ) {
        TOP_LEVEL_DESTINATIONS.forEach { destination ->
            val isSelected = if(selectedDestinationRoute == null) {
                selectedDestination == destination.route.name
            } else {
                selectedDestinationRoute == destination.route || selectedDestinationRoute.parent == destination.route
            }

            NavigationBarItem(
                colors =  NavigationBarItemDefaults.colors(
                    selectedIconColor = colorResource(id = R.color.accent_default),
                    unselectedIconColor = colorResource(id = R.color.fg_subtle),
                    indicatorColor = colorResource(id = R.color.bg_muted)
                ),
                selected = isSelected,
                onClick = { navigateToTopLevelDestination(destination) },
                icon = {
                    Icon(
                        modifier = Modifier
                            .width(28.dp)
                            .height(28.dp),
                        painter = painterResource(id = destination.selectedIconId),
                        contentDescription = stringResource(id = destination.iconTextId)
                    )
                },
            )
        }
    }
}