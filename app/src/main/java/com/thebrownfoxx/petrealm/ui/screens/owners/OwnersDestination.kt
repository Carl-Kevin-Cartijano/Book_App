package com.thebrownfoxx.petrealm.ui.screens.owners

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.thebrownfoxx.petrealm.application
import com.thebrownfoxx.petrealm.ui.components.RemoveDialogStateChangeListener
import com.thebrownfoxx.petrealm.ui.screens.navhost.OwnerNavGraph

@OwnerNavGraph(start = true)
@Destination
@Composable
fun Owners() {
    val viewModel = viewModel { OwnersViewModel(application.database) }

    with(viewModel) {
        val owners by owners.collectAsStateWithLifecycle()
        val selectedOwner by selectedOwner.collectAsStateWithLifecycle()
        val searchQuery by searchQuery.collectAsStateWithLifecycle()
        val removeDialogState by removeDialogState.collectAsStateWithLifecycle()

        OwnersScreen(
            owners = owners,
            selectedOwner = selectedOwner,
            onSelectedOwnerChange = ::updateSelectedOwner,
            searchQuery = searchQuery,
            onSearchQueryChange = ::updateSearchQuery,
            removeDialogState = removeDialogState,
            removeDialogStateChangeListener = RemoveDialogStateChangeListener(
                onInitiateRemove = ::initiateRemove,
                onCancelRemove = ::cancelRemove,
                onRemove = ::remove,
            )
        )
    }
}