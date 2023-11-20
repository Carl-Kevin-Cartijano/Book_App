package com.thebrownfoxx.petrealm.ui.screens.pets.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.thebrownfoxx.components.FilledButton
import com.thebrownfoxx.components.HorizontalSpacer
import com.thebrownfoxx.components.TextButton
import com.thebrownfoxx.components.VerticalSpacer
import com.thebrownfoxx.components.extension.rememberMutableStateOf
import com.thebrownfoxx.petrealm.models.PetType
import com.thebrownfoxx.petrealm.ui.components.TextField
import com.thebrownfoxx.petrealm.ui.screens.pets.state.AddPetDialogState
import com.thebrownfoxx.petrealm.ui.screens.pets.state.AddPetDialogStateChangeListener

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPetDialog(
    petTypes: List<PetType>,
    state: AddPetDialogState,
    stateChangeListener: AddPetDialogStateChangeListener,
    modifier: Modifier = Modifier,
) {
    var focusManager by rememberMutableStateOf<FocusManager?>(null)

    if (state is AddPetDialogState.Visible) {
        AlertDialog(
            modifier = modifier.clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
            ) { focusManager?.clearFocus(true) },
            onDismissRequest = stateChangeListener.onHideAddPetDialog,
            title = { Text(text = "Add Pet") },
            text = {
                // The focus manager should be from inside the AlertDialog
                focusManager = LocalFocusManager.current

                Column {
                    TextField(
                        label = "Name",
                        value = state.petName,
                        onValueChange = stateChangeListener.onPetNameChange,
                        error = if (state.hasPetAgeWarning) "Required" else null,
                    )
                    VerticalSpacer(height = 16.dp)
                    Row {
                        TextField(
                            label = "Age",
                            value = state.petAge?.toString() ?: "",
                            onValueChange = stateChangeListener.onPetAgeChange,
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
                            hasWarning = state.hasPetAgeWarning,
                            modifier = Modifier.weight(2f),
                        )
                    }
                    VerticalSpacer(height = 8.dp)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = state.hasOwner,
                            onCheckedChange = stateChangeListener.onHasOwnerChange,
                        )
                        Text(text = "Has owner?")
                    }
                    VerticalSpacer(height = 8.dp)
                    TextField(
                        value = state.ownerName,
                        onValueChange = stateChangeListener.onOwnerNameChange,
                        label = "Owner name",
                        enabled = state.hasOwner,
                        error = if (state.hasOwnerNameWarning) "Required" else null,
                    )
                }
            },
            dismissButton = {
                TextButton(
                    text = "Cancel",
                    onClick = stateChangeListener.onHideAddPetDialog,
                )
            },
            confirmButton = { FilledButton(text = "Add", onClick = stateChangeListener.onAddPet) }
        )
    }
}