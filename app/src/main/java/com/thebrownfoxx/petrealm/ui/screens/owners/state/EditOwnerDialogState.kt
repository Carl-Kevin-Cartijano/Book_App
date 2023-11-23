package com.thebrownfoxx.petrealm.ui.screens.owners.state

import com.thebrownfoxx.petrealm.models.Owner

sealed class EditOwnerDialogState {
    data object Hidden : EditOwnerDialogState()
    data class Pending(
        val owner: Owner,
        val newOwnerName: String,
        val hasOwnerNameWarning: Boolean,
        val ownerNameDuplicate: Boolean,
    ) : EditOwnerDialogState() {
        val hasWarning = hasOwnerNameWarning || ownerNameDuplicate
    }

    companion object {
        fun Pending(owner: Owner) = Pending(
            owner = owner,
            newOwnerName = owner.name,
            hasOwnerNameWarning = false,
            ownerNameDuplicate = false,
        )
    }
}

class EditOwnerDialogStateChangeListener(
    val onInitiateEdit: (Owner) -> Unit,
    val onOwnerNameChange: (String) -> Unit,
    val onCancelEdit: () -> Unit,
    val onSave: () -> Unit,
)