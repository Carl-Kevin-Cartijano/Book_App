package com.thebrownfoxx.petrealm.ui.screens.navhost

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun NavigationBar(
    currentNavGraph: NavigationBarGraph,
    onNavGraphChange: (NavigationBarGraph) -> Unit,
    modifier: Modifier = Modifier,
) {
    NavigationBar(modifier = modifier) {
        for (navGraph in NavigationBarGraph.entries) {
            val selected = navGraph == currentNavGraph
            NavigationBarItem(
                selected = selected,
                onClick = {
                    if (!selected) onNavGraphChange(navGraph)
                },
                icon = { Icon(imageVector = navGraph.icon, contentDescription = null) },
                label = { Text(text = navGraph.label) },
            )
        }
    }
}