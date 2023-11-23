package com.thebrownfoxx.petrealm.ui.screens.pets.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
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
import com.thebrownfoxx.petrealm.ui.components.PetCard
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
            title = { Text(text = "Adopt pet") },
            text = {
                // The focus manager should be from inside the AlertDialog
                focusManager = LocalFocusManager.current
                Column {
                    PetCard(
                        petName = state.pet.name,
                        petAge = state.pet.age.toString(),
                        petType = state.pet.type.name,
                        ownerName = state.ownerName,
                    )
                    VerticalSpacer(height = 16.dp)
                    TextField(
                        label = "Owner name",
                        value = state.ownerName,
                        onValueChange = stateChangeListener.onOwnerNameChange,
                        required = true,
                        isError = state.hasOwnerNameWarning,
                    )
                    Text(
                        text = "Required",
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.padding(start = 16.dp, top = 4.dp),
                        color = if (state.hasOwnerNameWarning) colorScheme.error else colorScheme.onSurface,
                    )
                }
            },
            dismissButton = {
                TextButton(
                    text = "Cancel",
                    onClick = stateChangeListener.onCancelAdopt,
                )
            },
            confirmButton = { FilledButton(text = "Adopt", onClick = stateChangeListener.onAdopt) }
        )
    }
}