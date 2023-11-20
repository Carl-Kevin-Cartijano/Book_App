package com.thebrownfoxx.petrealm.ui.screens.owners

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.thebrownfoxx.petrealm.application
import com.thebrownfoxx.petrealm.ui.screens.navhost.OwnerNavGraph

@OwnerNavGraph(start = true)
@Destination
@Composable
fun Owners(navigator: DestinationsNavigator) {
    val viewModel = viewModel { OwnersViewModel(application.database) }

    with(viewModel) {
        val owners by owners.collectAsStateWithLifecycle()

        OwnersScreen(owners = owners)
    }
}