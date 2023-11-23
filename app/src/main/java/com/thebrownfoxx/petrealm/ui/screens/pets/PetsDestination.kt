package com.thebrownfoxx.petrealm.ui.screens.pets

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.thebrownfoxx.petrealm.application
import com.thebrownfoxx.petrealm.ui.components.RemoveDialogStateChangeListener
import com.thebrownfoxx.petrealm.ui.screens.destinations.AddPetDestination
import com.thebrownfoxx.petrealm.ui.screens.navhost.PetNavGraph
import com.thebrownfoxx.petrealm.ui.screens.pets.state.AdoptDialogStateChangeListener
import com.thebrownfoxx.petrealm.ui.screens.pets.state.PetsViewModel

@PetNavGraph(start = true)
@Destination
@Composable
fun Pets(navigator: DestinationsNavigator) {
    val viewModel = viewModel { PetsViewModel(application.database) }

    with(viewModel) {
        val pets by pets.collectAsStateWithLifecycle()
        val selectedPet by selectedPet.collectAsStateWithLifecycle()
        val searchQuery by searchQuery.collectAsStateWithLifecycle()
        val adoptDialogState by adoptDialogState.collectAsStateWithLifecycle()
        val removeDialogState by removeDialogState.collectAsStateWithLifecycle()

        PetsScreen(
            pets = pets,
            selectedPet = selectedPet,
            onSelectedPetChange = ::updateSelectedPet,
            searchQuery = searchQuery,
            onSearchQueryChange = ::updateSearchQuery,
            onAddPet = { navigator.navigate(AddPetDestination) },
            adoptDialogState = adoptDialogState,
            adoptDialogStateChangeListener = AdoptDialogStateChangeListener(
                onInitiateAdopt = ::initiateAdopt,
                onOwnerNameChange = ::updateOwnerName,
                onCancelAdopt = ::cancelAdopt,
                onAdopt = ::adopt,
            ),
            onEditPet = { TODO() },
            removeDialogState = removeDialogState,
            removeDialogStateChangeListener = RemoveDialogStateChangeListener(
                onInitiateRemove = ::initiateRemove,
                onCancelRemove = ::cancelRemove,
                onRemove = ::remove
            )
        )
    }
}