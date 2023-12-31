package com.thebrownfoxx.petrealm.ui.screens.addpet

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.thebrownfoxx.petrealm.application
import com.thebrownfoxx.petrealm.ui.screens.navhost.PetNavGraph

@PetNavGraph
@Destination
@Composable
fun AddPet(navigator: DestinationsNavigator) {
    val viewModel = viewModel { AddPetViewModel(application.database) }

    with(viewModel) {
        val petTypes by petTypes.collectAsStateWithLifecycle()
        val state by state.collectAsStateWithLifecycle()

        LaunchedEffect(Unit) {
            navigateUp.collect {
                navigator.navigateUp()
            }
        }

        AddPetScreen(
            petTypes = petTypes,
            state = state,
            stateChangeListener = AddPetStateChangeListener(
                onPetNameChange = ::updatePetName,
                onPetAgeChange = ::updatePetAge,
                onPetTypeDropdownExpandedChange = ::updatePetTypeDropdownExpanded,
                onPetTypeChange = ::updatePetType,
                onOwnerNameChange = ::updateOwnerName,
                onAddPet = ::addPet
            ),
            onNavigateUp = navigator::navigateUp,
        )
    }
}