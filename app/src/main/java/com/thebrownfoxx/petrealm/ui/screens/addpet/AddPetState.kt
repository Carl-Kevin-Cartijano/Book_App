package com.thebrownfoxx.petrealm.ui.screens.addpet

import com.thebrownfoxx.petrealm.models.PetType

data class AddPetState(
    val petName: String = "",
    val petAge: Int? = null,
    val petTypeDropdownExpanded: Boolean = false,
    val petType: PetType? = null,
    val ownerName: String = "",
    val hasPetNameWarning: Boolean = false,
    val hasPetAgeWarning: Boolean = false,
    val hasPetTypeWarning: Boolean = false,
) {
    val hasWarning =
        hasPetNameWarning || hasPetAgeWarning || hasPetTypeWarning
}

class AddPetStateChangeListener(
    val onPetNameChange: (String) -> Unit,
    val onPetAgeChange: (String) -> Unit,
    val onPetTypeDropdownExpandedChange: (Boolean) -> Unit,
    val onPetTypeChange: (PetType) -> Unit,
    val onOwnerNameChange: (String) -> Unit,
    val onAddPet: () -> Unit
)
