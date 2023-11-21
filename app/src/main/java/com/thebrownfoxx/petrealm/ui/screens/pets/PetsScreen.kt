package com.thebrownfoxx.petrealm.ui.screens.pets

import androidx.compose.foundation.ExperimentalFoundationApi
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
import com.thebrownfoxx.petrealm.ui.components.RemoveDialogState
import com.thebrownfoxx.petrealm.ui.components.RemoveDialogStateChangeListener
import com.thebrownfoxx.petrealm.ui.components.SearchableLazyColumnScreen
import com.thebrownfoxx.petrealm.ui.components.getListState
import com.thebrownfoxx.petrealm.ui.screens.pets.components.AdoptDialog
import com.thebrownfoxx.petrealm.ui.screens.pets.components.RemovePetDialog
import com.thebrownfoxx.petrealm.ui.screens.pets.components.SwipeablePetCard
import com.thebrownfoxx.petrealm.ui.screens.pets.state.AdoptDialogState
import com.thebrownfoxx.petrealm.ui.screens.pets.state.AdoptDialogStateChangeListener
import com.thebrownfoxx.petrealm.ui.theme.AppTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PetsScreen(
    pets: List<Pet>?,
    selectedPet: Pet?,
    onSelectedPetChange: (Pet?) -> Unit,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    addPet: () -> Unit,
//    petTypes: List<PetType>,
//    addPetDialogState: AddPetDialogState,
//    addPetDialogStateChangeListener: AddPetDialogStateChangeListener,
    adoptDialogState: AdoptDialogState,
    adoptDialogStateChangeListener: AdoptDialogStateChangeListener,
    removeDialogState: RemoveDialogState<Pet>,
    removeDialogStateChangeListener: RemoveDialogStateChangeListener<Pet>,
    modifier: Modifier = Modifier,
) {
    SearchableLazyColumnScreen(
        modifier = modifier,
        topBarExpandedContent = { Text(text = "Pets") },
        searchQuery = searchQuery,
        onSearchQueryChange = onSearchQueryChange,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(vertical = 16.dp),
        listState = pets.getListState("No pets registered"),
        floatingActionButton = {
            FloatingActionButton(onClick = addPet) {
                Icon(imageVector = Icons.TwoTone.Add, contentDescription = null)
            }
        }
    ) {
        items(
            items = pets ?: emptyList(),
            key = { it.id }
        ) { pet ->
            val selected = pet == selectedPet

            SwipeablePetCard(
                pet = pet,
                expanded = selected,
                onClick = {
                    if (!selected) onSelectedPetChange(pet) else onSelectedPetChange(null)
                },
                onInitiateAdopt = { adoptDialogStateChangeListener.onInitiateAdopt(pet) },
                onInitiateRemove = { removeDialogStateChangeListener.onInitiateRemove(pet) },
                contentPadding = PaddingValues(horizontal = 16.dp),
                modifier = Modifier.animateItemPlacement(),
            )
        }
    }
    AdoptDialog(
        state = adoptDialogState,
        stateChangeListener = adoptDialogStateChangeListener,
    )
    RemovePetDialog(
        state = removeDialogState,
        stateChangeListener = removeDialogStateChangeListener,
    )
}

@Preview
@Composable
fun PetsScreenPreview() {
    AppTheme {
        PetsScreen(
            pets = Sample.Pets,
            selectedPet = null,
            onSelectedPetChange = {},
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
            adoptDialogState = AdoptDialogState.Hidden,
            adoptDialogStateChangeListener = AdoptDialogStateChangeListener(
                onInitiateAdopt = {},
                onCancelAdopt = {},
                onOwnerNameChange = {},
                onAdopt = {},
            ),
            removeDialogState = RemoveDialogState.Hidden(),
            removeDialogStateChangeListener = RemoveDialogStateChangeListener(
                onInitiateRemove = { true },
                onCancelRemove = {},
                onRemove = {},
            )
        )
    }
}