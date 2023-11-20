package com.thebrownfoxx.petrealm.ui.screens.pets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thebrownfoxx.petrealm.models.Pet
import com.thebrownfoxx.petrealm.models.Sample
import com.thebrownfoxx.petrealm.ui.components.SearchableLazyColumnScreen
import com.thebrownfoxx.petrealm.ui.screens.pets.components.PetCard
import com.thebrownfoxx.petrealm.ui.screens.pets.components.RemovePetDialog
import com.thebrownfoxx.petrealm.ui.screens.pets.state.RemovePetDialogState
import com.thebrownfoxx.petrealm.ui.screens.pets.state.RemovePetDialogStateChangeListener
import com.thebrownfoxx.petrealm.ui.theme.AppTheme

@Composable
fun PetsScreen(
    pets: List<Pet>,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    addPet: () -> Unit,
//    petTypes: List<PetType>,
//    addPetDialogState: AddPetDialogState,
//    addPetDialogStateChangeListener: AddPetDialogStateChangeListener,
    removePetDialogState: RemovePetDialogState,
    removePetDialogStateChangeListener: RemovePetDialogStateChangeListener,
    modifier: Modifier = Modifier,
) {
    SearchableLazyColumnScreen(
        modifier = modifier,
        topBarExpandedContent = { Text(text = "Pets") },
        searchQuery = searchQuery,
        onSearchQueryChange = onSearchQueryChange,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(vertical = 16.dp),
        floatingActionButton = {
            FloatingActionButton(onClick = addPet) {
                Icon(imageVector = Icons.TwoTone.Add, contentDescription = null)
            }
        }
    ) {
        items(pets) { pet ->
            PetCard(
                pet = pet,
                onRemove = { removePetDialogStateChangeListener.onInitiateRemovePet(pet) },
                contentPadding = PaddingValues(horizontal = 16.dp)
            )
        }
    }
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
            addPet = {},
//            petTypes = listOf(),
//            addPetDialogState = AddPetDialogState.Hidden,
//            addPetDialogStateChangeListener = AddPetDialogStateChangeListener(
//                onShowAddPetDialog = {},
//                onHideAddPetDialog = {},
//                onPetNameChange = {},
//                onPetAgeChange = {},
//                onPetTypeDropdownExpandedChange = {},
//                onPetTypeChange = {},
//                onOwnerNameChange = {},
//                onAddPet = {},
//            ),
            removePetDialogState = RemovePetDialogState.Hidden,
            removePetDialogStateChangeListener = RemovePetDialogStateChangeListener(
                onInitiateRemovePet = {},
                onCancelRemovePet = {},
                onRemovePet = {},
            )
        )
    }
}