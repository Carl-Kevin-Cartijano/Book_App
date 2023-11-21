package com.thebrownfoxx.petrealm.ui.screens.pets.state

import com.thebrownfoxx.petrealm.models.Pet

sealed class AdoptDialogState {
    data object Hidden : AdoptDialogState()
    data class Pending(
        val pet: Pet,
        val ownerName: String = "",
        val hasOwnerNameWarning: Boolean = false,
    ) : AdoptDialogState()
}

class AdoptDialogStateChangeListener(
    val onInitiateAdopt: (Pet) -> Unit,
    val onOwnerNameChange: (String) -> Unit,
    val onCancelAdopt: () -> Unit,
    val onAdopt: () -> Unit,
)