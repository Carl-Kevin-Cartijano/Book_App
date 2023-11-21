package com.thebrownfoxx.petrealm.ui.screens.owners.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.thebrownfoxx.components.FilledButton
import com.thebrownfoxx.components.TextButton
import com.thebrownfoxx.components.VerticalSpacer
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
            title = { Text(text = "Unregister owner") },
            text = {
                Column {
                    Text(
                        text = "Are you sure you want to unregister this owner? " +
                                "All their pets will be unowned",
                    )
                    VerticalSpacer(height = 16.dp)
                    OwnerCard(
                        owner = state.value,
                        expanded = true,
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