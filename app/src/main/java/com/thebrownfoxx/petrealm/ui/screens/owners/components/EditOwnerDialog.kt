package com.thebrownfoxx.petrealm.ui.screens.owners.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.thebrownfoxx.components.FilledButton
import com.thebrownfoxx.components.TextButton
import com.thebrownfoxx.components.VerticalSpacer
import com.thebrownfoxx.components.extension.rememberMutableStateOf
import com.thebrownfoxx.petrealm.ui.components.TextField
import com.thebrownfoxx.petrealm.ui.components.indicationlessClickable
import com.thebrownfoxx.petrealm.ui.screens.owners.state.EditOwnerDialogState
import com.thebrownfoxx.petrealm.ui.screens.owners.state.EditOwnerDialogStateChangeListener

@Composable
fun EditOwnerDialog(
    state: EditOwnerDialogState,
    stateChangeListener: EditOwnerDialogStateChangeListener,
    modifier: Modifier = Modifier,
) {
    var focusManager by rememberMutableStateOf<FocusManager?>(null)

    if (state is EditOwnerDialogState.Pending) {
        AlertDialog(
            modifier = modifier.indicationlessClickable {
                focusManager?.clearFocus(true)
            },
            onDismissRequest = stateChangeListener.onCancelEdit,
            title = { Text(text = "Edit owner") },
            text = {
                // The focus manager should be from inside the AlertDialog
                focusManager = LocalFocusManager.current
                Column {
                    OwnerCard(
                        owner = state.owner.copy(name = state.newOwnerName),
                        expanded = true,
                    )
                    VerticalSpacer(height = 16.dp)
                    TextField(
                        label = "New name",
                        value = state.newOwnerName,
                        onValueChange = stateChangeListener.onOwnerNameChange,
                        required = true,
                        isError = state.hasWarning,
                    )
                    Text(
                        text = if (state.ownerNameDuplicate) "Owners cannot have the same name" else "Required",
                        style = typography.labelSmall,
                        modifier = Modifier.padding(start = 16.dp, top = 4.dp),
                        color = if (state.hasWarning) colorScheme.error else colorScheme.onSurface,
                    )
                }
            },
            dismissButton = {
                TextButton(
                    text = "Cancel",
                    onClick = stateChangeListener.onCancelEdit,
                )
            },
            confirmButton = {
                FilledButton(
                    text = "Save",
                    onClick = stateChangeListener.onSave,
                )
            }
        )
    }
}