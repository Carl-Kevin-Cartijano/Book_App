package com.thebrownfoxx.petrealm.ui.screens.pets.state

import com.thebrownfoxx.petrealm.models.PetType

sealed class AddPetDialogState {
    data object Hidden : AddPetDialogState()
    data class Visible(
        val petName: String = "",
        val petAge: Int? = null,
        val petTypeDropdownExpanded: Boolean = false,
        val petType: PetType? = null,
        val ownerName: String = "",
        val hasPetNameWarning: Boolean = false,
        val hasPetAgeWarning: Boolean = false,
        val hasPetTypeWarning: Boolean = false,
    ) : AddPetDialogState() {
        val hasWarning =
            hasPetNameWarning || hasPetAgeWarning || hasPetTypeWarning
    }
}

class AddPetDialogStateChangeListener(
    val onShowAddPetDialog: () -> Unit,
    val onHideAddPetDialog: () -> Unit,
    val onPetNameChange: (String) -> Unit,
    val onPetAgeChange: (String) -> Unit,
    val onPetTypeDropdownExpandedChange: (Boolean) -> Unit,
    val onPetTypeChange: (PetType) -> Unit,
    val onOwnerNameChange: (String) -> Unit,
    val onAddPet: () -> Unit
)
