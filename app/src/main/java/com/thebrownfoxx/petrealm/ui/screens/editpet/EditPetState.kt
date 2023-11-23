package com.thebrownfoxx.petrealm.ui.screens.editpet

import com.thebrownfoxx.petrealm.models.Pet
import com.thebrownfoxx.petrealm.models.PetType

data class EditPetState(
    val petId: String,
    val petName: String,
    val petAge: Int?,
    val petTypeDropdownExpanded: Boolean = false,
    val petType: PetType?,
    val ownerName: String,
    val hasPetNameWarning: Boolean = false,
    val hasPetAgeWarning: Boolean = false,
    val hasPetTypeWarning: Boolean = false,
) {
    val hasWarning =
        hasPetNameWarning || hasPetAgeWarning || hasPetTypeWarning
}

fun EditPetState(pet: Pet) = EditPetState(
    petId = pet.id,
    petName = pet.name,
    petAge = pet.age,
    petType = pet.type,
    ownerName = pet.owner?.name ?: "",
)

class EditPetStateChangeListener(
    val onPetNameChange: (String) -> Unit,
    val onPetAgeChange: (String) -> Unit,
    val onPetTypeDropdownExpandedChange: (Boolean) -> Unit,
    val onPetTypeChange: (PetType) -> Unit,
    val onOwnerNameChange: (String) -> Unit,
    val onEditPet: () -> Unit
)
