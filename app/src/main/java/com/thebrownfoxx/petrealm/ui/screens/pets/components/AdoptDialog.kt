package com.thebrownfoxx.petrealm.ui.screens.pets.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import com.thebrownfoxx.components.FilledButton
import com.thebrownfoxx.components.TextButton
import com.thebrownfoxx.components.extension.rememberMutableStateOf
import com.thebrownfoxx.petrealm.ui.components.TextField
import com.thebrownfoxx.petrealm.ui.components.indicationlessClickable
import com.thebrownfoxx.petrealm.ui.screens.pets.state.AdoptDialogState
import com.thebrownfoxx.petrealm.ui.screens.pets.state.AdoptDialogStateChangeListener

@Composable
fun AdoptDialog(
    state: AdoptDialogState,
    stateChangeListener: AdoptDialogStateChangeListener,
    modifier: Modifier = Modifier,
) {
    var focusManager by rememberMutableStateOf<FocusManager?>(null)

    if (state is AdoptDialogState.Pending) {
        AlertDialog(
            modifier = modifier.indicationlessClickable {
                focusManager?.clearFocus(true)
            },
            onDismissRequest = stateChangeListener.onCancelAdopt,
            title = { Text(text = "Add Pet") },
            text = {
                // The focus manager should be from inside the AlertDialog
                focusManager = LocalFocusManager.current

                TextField(
                    label = "Owner name",
                    value = state.ownerName,
                    onValueChange = stateChangeListener.onOwnerNameChange,
                    required = true,
                    error = if (state.hasOwnerNameWarning) "Required" else null,
                )
            },
            dismissButton = {
                TextButton(
                    text = "Cancel",
                    onClick = stateChangeListener.onCancelAdopt,
                )
            },
            confirmButton = { FilledButton(text = "Add", onClick = stateChangeListener.onAdopt) }
        )
    }
}