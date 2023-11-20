package com.thebrownfoxx.petrealm.ui.screens.pets.state

import com.thebrownfoxx.petrealm.models.Pet

sealed class RemovePetDialogState {
    data object Hidden: RemovePetDialogState()
    data class Visible(val pet: Pet): RemovePetDialogState()
}

class RemovePetDialogStateChangeListener(
    val onInitiateRemovePet: (Pet) -> Unit,
    val onCancelRemovePet: () -> Unit,
    val onRemovePet: () -> Unit
)