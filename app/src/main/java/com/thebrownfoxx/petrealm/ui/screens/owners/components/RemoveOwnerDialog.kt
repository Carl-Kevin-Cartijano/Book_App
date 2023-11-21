package com.thebrownfoxx.ownerrealm.ui.screens.owners.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.thebrownfoxx.components.FilledButton
import com.thebrownfoxx.components.TextButton
import com.thebrownfoxx.petrealm.models.Owner
import com.thebrownfoxx.petrealm.ui.components.RemoveDialogState
import com.thebrownfoxx.petrealm.ui.components.RemoveDialogStateChangeListener

@Composable
fun RemoveOwnerDialog(
    state: RemoveDialogState<Owner>,
    stateChangeListener: RemoveDialogStateChangeListener<Owner>,
    modifier: Modifier = Modifier,
) {
    if (state is RemoveDialogState.Pending) {
        AlertDialog(
            modifier = modifier,
            onDismissRequest = stateChangeListener.onCancelRemove,
            title = { Text(text = "Remove Owner") },
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