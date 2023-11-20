package com.thebrownfoxx.petrealm.ui.screens.addpet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.thebrownfoxx.components.FilledButton
import com.thebrownfoxx.components.HorizontalSpacer
import com.thebrownfoxx.components.TextButton
import com.thebrownfoxx.components.VerticalSpacer
import com.thebrownfoxx.petrealm.models.PetType
import com.thebrownfoxx.petrealm.ui.components.TextField
import com.thebrownfoxx.petrealm.ui.screens.pets.components.PetTypeDropdownMenu

@Composable
fun AddPetScreen(
    petTypes: List<PetType>,
    state: AddPetState,
    stateChangeListener: AddPetStateChangeListener,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        TextField(
            label = "Name",
            value = state.petName,
            onValueChange = stateChangeListener.onPetNameChange,
            required = true,
            error = if (state.hasPetNameWarning) "Required" else null,
        )
        VerticalSpacer(height = 16.dp)
        Row {
            TextField(
                label = "Age",
                value = state.petAge?.toString() ?: "",
                onValueChange = stateChangeListener.onPetAgeChange,
                required = true,
                error = if (state.hasPetAgeWarning) "Required" else null,
                modifier = Modifier.weight(1f),
            )
            HorizontalSpacer(width = 16.dp)
            PetTypeDropdownMenu(
                selectedPetType = state.petType,
                petTypes = petTypes,
                expanded = state.petTypeDropdownExpanded,
                onExpandedChange = stateChangeListener.onPetTypeDropdownExpandedChange,
                onPetTypeChange = stateChangeListener.onPetTypeChange,
                hasWarning = state.hasPetTypeWarning,
                modifier = Modifier.weight(2f),
            )
        }
        VerticalSpacer(height = 16.dp)
        TextField(
            label = "Owner name",
            value = state.ownerName,
            onValueChange = stateChangeListener.onOwnerNameChange,
        )
        VerticalSpacer(height = 16.dp)
        Row {
            TextButton(
                text = "Cancel",
                onClick = onNavigateUp,
            )
            FilledButton(text = "Add", onClick = stateChangeListener.onAddPet)
        }
    }
}