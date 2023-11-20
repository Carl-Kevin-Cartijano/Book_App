package com.thebrownfoxx.petrealm.ui.screens.pets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thebrownfoxx.components.extension.Elevation
import com.thebrownfoxx.components.extension.plus
import com.thebrownfoxx.petrealm.models.Pet
import com.thebrownfoxx.petrealm.models.PetType
import com.thebrownfoxx.petrealm.models.Sample
import com.thebrownfoxx.petrealm.ui.screens.pets.components.AddPetDialog
import com.thebrownfoxx.petrealm.ui.screens.pets.components.PetCard
import com.thebrownfoxx.petrealm.ui.screens.pets.components.RemovePetDialog
import com.thebrownfoxx.petrealm.ui.screens.pets.state.AddPetDialogState
import com.thebrownfoxx.petrealm.ui.screens.pets.state.AddPetDialogStateChangeListener
import com.thebrownfoxx.petrealm.ui.screens.pets.state.RemovePetDialogState
import com.thebrownfoxx.petrealm.ui.screens.pets.state.RemovePetDialogStateChangeListener
import com.thebrownfoxx.petrealm.ui.theme.AppTheme

@Composable
fun PetsScreen(
    pets: List<Pet>,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    petTypes: List<PetType>,
    addPetDialogState: AddPetDialogState,
    addPetDialogStateChangeListener: AddPetDialogStateChangeListener,
    removePetDialogState: RemovePetDialogState,
    removePetDialogStateChangeListener: RemovePetDialogStateChangeListener,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            Surface(tonalElevation = Elevation.level(3)) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = onSearchQueryChange,
                    label = { Text(text = "Search") },
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = addPetDialogStateChangeListener.onShowAddPetDialog) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        },
    ) { contentPadding ->
        LazyColumn(
            contentPadding = contentPadding + PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            items(pets) { pet ->
                PetCard(
                    pet = pet,
                    onRemove = { removePetDialogStateChangeListener.onInitiateRemovePet(pet) },
                )
            }
        }
    }

    AddPetDialog(
        petTypes = petTypes,
        state = addPetDialogState,
        stateChangeListener = addPetDialogStateChangeListener,
    )
    RemovePetDialog(
        state = removePetDialogState,
        stateChangeListener = removePetDialogStateChangeListener,
    )
}

@Preview
@Composable
fun PetsScreenPreview() {
    AppTheme {
        PetsScreen(
            pets = Sample.Pets,
            searchQuery = "",
            onSearchQueryChange = {},
            petTypes = listOf(),
            addPetDialogState = AddPetDialogState.Hidden,
            addPetDialogStateChangeListener = AddPetDialogStateChangeListener(
                onShowAddPetDialog = {},
                onHideAddPetDialog = {},
                onPetNameChange = {},
                onPetAgeChange = {},
                onPetTypeDropdownExpandedChange = {},
                onPetTypeChange = {},
                onOwnerNameChange = {},
                onAddPet = {},
            ),
            removePetDialogState = RemovePetDialogState.Hidden,
            removePetDialogStateChangeListener = RemovePetDialogStateChangeListener(
                onInitiateRemovePet = {},
                onCancelRemovePet = {},
                onRemovePet = {},
            )
        )
    }
}