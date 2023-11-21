package com.thebrownfoxx.petrealm.ui.screens.pets.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.thebrownfoxx.components.FilledButton
import com.thebrownfoxx.components.TextButton
import com.thebrownfoxx.components.VerticalSpacer
import com.thebrownfoxx.petrealm.models.Pet
import com.thebrownfoxx.petrealm.ui.components.PetCard
import com.thebrownfoxx.petrealm.ui.components.RemoveDialogState
import com.thebrownfoxx.petrealm.ui.components.RemoveDialogStateChangeListener

@Composable
fun RemovePetDialog(
    state: RemoveDialogState<Pet>,
    stateChangeListener: RemoveDialogStateChangeListener<Pet>,
    modifier: Modifier = Modifier,
) {
    if (state is RemoveDialogState.Pending) {
        AlertDialog(
            modifier = modifier,
            onDismissRequest = stateChangeListener.onCancelRemove,
            title = { Text(text = "Unregister pet") },
            text = {
                Column {
                    Text(text = "Are you sure you want to unregister this pet?")
                    VerticalSpacer(height = 16.dp)
                    PetCard(
                        petName = state.value.name,
                        petAge = state.value.age.toString(),
                        petType = state.value.type.name,
                        ownerName = state.value.owner?.name ?: "",
                    )
                }
            },
            dismissButton = {
                TextButton(
                    text = "No",
                    onClick = stateChangeListener.onCancelRemove,
                )
            },
            confirmButton = {
                FilledButton(
                    text = "Yes",
                    onClick = stateChangeListener.onRemove,
                )
            },
        )
    }
}