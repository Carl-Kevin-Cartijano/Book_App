package com.thebrownfoxx.petrealm.ui.screens.navhost

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Person
import androidx.compose.material.icons.twotone.Pets
import androidx.compose.ui.graphics.vector.ImageVector
import com.thebrownfoxx.petrealm.ui.screens.NavGraph
import com.thebrownfoxx.petrealm.ui.screens.NavGraphs

enum class NavigationBarGraph(
    val graph: NavGraph,
    val icon: ImageVector,
    val label: String,
) {
    Pet(
        graph = NavGraphs.pet,
        icon = Icons.TwoTone.Pets,
        label = "Pets",
    ),
    Owner(
        graph = NavGraphs.owner,
        icon = Icons.TwoTone.Person,
        label = "Owners",
    )
}

