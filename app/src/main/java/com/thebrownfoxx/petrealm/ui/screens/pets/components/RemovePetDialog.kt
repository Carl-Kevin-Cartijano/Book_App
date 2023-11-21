package com.thebrownfoxx.petrealm.ui.screens.pets.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.thebrownfoxx.components.FilledButton
import com.thebrownfoxx.components.TextButton
import com.thebrownfoxx.petrealm.models.Pet
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
            title = { Text(text = "Remove Pet") },
            text = { Text(text = "Are you sure you want to remove ${state.value.name}?") },
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