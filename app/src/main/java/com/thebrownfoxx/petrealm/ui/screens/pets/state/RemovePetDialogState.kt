package com.thebrownfoxx.petrealm.ui.screens.pets.state

import com.thebrownfoxx.petrealm.models.Pet

sealed class RemovePetDialogState {
    data object Hidden : RemovePetDialogState()
    data class Pending(val pet: Pet) : RemovePetDialogState()
    data object Canceled : RemovePetDialogState()
    data object Confirmed : RemovePetDialogState()
}

class RemovePetDialogStateChangeListener(
    val onInitiateRemovePet: suspend (Pet) -> Boolean,
    val onCancelRemovePet: () -> Unit,
    val onRemovePet: () -> Unit,
)