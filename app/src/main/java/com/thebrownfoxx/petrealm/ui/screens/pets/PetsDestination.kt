package com.thebrownfoxx.petrealm.ui.screens.pets

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.thebrownfoxx.petrealm.application
import com.thebrownfoxx.petrealm.ui.screens.destinations.AddPetDestination
import com.thebrownfoxx.petrealm.ui.screens.navhost.PetNavGraph
import com.thebrownfoxx.petrealm.ui.screens.pets.state.PetsViewModel
import com.thebrownfoxx.petrealm.ui.screens.pets.state.RemovePetDialogStateChangeListener

@PetNavGraph(start = true)
@Destination
@Composable
fun Pets(navigator: DestinationsNavigator) {
    val viewModel = viewModel { PetsViewModel(application.database) }

    with(viewModel) {
        val pets by pets.collectAsStateWithLifecycle()
        val searchQuery by searchQuery.collectAsStateWithLifecycle()
        /*val petTypes by petTypes.collectAsStateWithLifecycle()
        val addPetDialogState by addPetDialogState.collectAsStateWithLifecycle()*/
        val removePetDialogState by removePetDialogState.collectAsStateWithLifecycle()

        PetsScreen(
            pets = pets,
            searchQuery = searchQuery,
            onSearchQueryChange = ::updateSearchQuery,
            addPet = { navigator.navigate(AddPetDestination) },
            /*petTypes = petTypes,
            addPetDialogState = addPetDialogState,
            addPetDialogStateChangeListener = AddPetDialogStateChangeListener(
                onShowAddPetDialog = ::showAddPetDialog,
                onHideAddPetDialog = ::hideAddPetDialog,
                onPetNameChange = ::updatePetName,
                onPetAgeChange = ::updatePetAge,
                onPetTypeDropdownExpandedChange = ::updatePetTypeDropdownExpanded,
                onPetTypeChange = ::updatePetType,
                onOwnerNameChange = ::updateOwnerName,
                onAddPet = ::addPet
            ),*/
            removePetDialogState = removePetDialogState,
            removePetDialogStateChangeListener = RemovePetDialogStateChangeListener(
                onInitiateRemovePet = ::initiateRemovePet,
                onCancelRemovePet = ::cancelRemovePet,
                onRemovePet = ::removePet
            )
        )
    }
}