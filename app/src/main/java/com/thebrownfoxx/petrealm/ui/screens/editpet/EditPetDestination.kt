package com.thebrownfoxx.petrealm.ui.screens.editpet

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.thebrownfoxx.petrealm.application
import com.thebrownfoxx.petrealm.ui.screens.navhost.PetNavGraph

@PetNavGraph
@Destination(navArgsDelegate = EditPetNavArgs::class)
@Composable
fun EditPet(navigator: DestinationsNavigator) {
    val viewModel = viewModel {
        EditPetViewModel(
            database = application.database,
            savedStateHandle = createSavedStateHandle(),
        )
    }

    with(viewModel) {
        val petTypes by petTypes.collectAsStateWithLifecycle()
        val state by state.collectAsStateWithLifecycle()

        LaunchedEffect(Unit) {
            navigateUp.collect {
                navigator.navigateUp()
            }
        }

        EditPetScreen(
            petTypes = petTypes,
            state = state,
            stateChangeListener = EditPetStateChangeListener(
                onPetNameChange = ::updatePetName,
                onPetAgeChange = ::updatePetAge,
                onPetTypeDropdownExpandedChange = ::updatePetTypeDropdownExpanded,
                onPetTypeChange = ::updatePetType,
                onOwnerNameChange = ::updateOwnerName,
                onEditPet = ::editPet
            ),
            onNavigateUp = navigator::navigateUp,
        )
    }
}