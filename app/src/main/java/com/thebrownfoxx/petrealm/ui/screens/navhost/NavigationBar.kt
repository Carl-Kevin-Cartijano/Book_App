package com.thebrownfoxx.petrealm.ui.screens.navhost

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.ramcosta.composedestinations.navigation.navigate
import com.thebrownfoxx.petrealm.ui.screens.NavGraphs
import com.thebrownfoxx.petrealm.ui.screens.appCurrentDestinationAsState
import com.thebrownfoxx.petrealm.ui.screens.destinations.Destination
import com.thebrownfoxx.petrealm.ui.screens.startAppDestination

@Composable
fun NavigationBar(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val currentDestination: Destination = navController.appCurrentDestinationAsState().value
        ?: NavGraphs.root.startAppDestination

    NavigationBar(modifier = modifier) {
        NavigationBarDestination.entries.forEach { destination ->
            val selected = currentDestination == destination.direction
            NavigationBarItem(
                selected = selected,
                onClick = {
                    if (!selected) navController.navigate(destination.direction)
                },
                icon = { Icon(imageVector = destination.icon, contentDescription = null) },
                label = { Text(text = destination.label) },
            )
        }
    }
}