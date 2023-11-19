package com.thebrownfoxx.petrealm.ui.screens.navhost

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Person
import androidx.compose.material.icons.twotone.Pets
import androidx.compose.ui.graphics.vector.ImageVector
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec
import com.thebrownfoxx.petrealm.ui.screens.destinations.OwnersDestination
import com.thebrownfoxx.petrealm.ui.screens.destinations.PetsDestination

enum class NavigationBarDestination(
    val direction: DirectionDestinationSpec,
    val icon: ImageVector,
    val label: String,
) {
    Pets(
        direction = PetsDestination,
        icon = Icons.TwoTone.Pets,
        label = "Pets",
    ),
    Owners(
        direction = OwnersDestination,
        icon = Icons.TwoTone.Person,
        label = "Owners",
    )
}

