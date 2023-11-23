package com.thebrownfoxx.petrealm.ui.screens.editpet

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hamthelegend.enchantmentorder.extensions.mapToStateFlow
import com.thebrownfoxx.petrealm.models.Owner
import com.thebrownfoxx.petrealm.models.Pet
import com.thebrownfoxx.petrealm.models.PetType
import com.thebrownfoxx.petrealm.realm.PetRealmDatabase
import com.thebrownfoxx.petrealm.ui.screens.navArgs
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EditPetViewModel(
    private val database: PetRealmDatabase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val petId = savedStateHandle.navArgs<EditPetNavArgs>().petId
    private val realmPet = database.getPet(org.mongodb.kbson.ObjectId(petId))!!
    private val pet = Pet(
        id = realmPet.id.toHexString(),
        name = realmPet.name,
        age = realmPet.age,
        type = PetType(
            id = realmPet.type!!.id.toHexString(),
            name = realmPet.type!!.name,
        ),
        owner = realmPet.owner?.let { realmOwner ->
            Owner(
                id = realmOwner.id.toHexString(),
                name = realmOwner.name,
                pets = emptyList(),
            )
        },
    )

    private val _navigateUp = MutableSharedFlow<Boolean>()
    val navigateUp = _navigateUp.asSharedFlow()

    val petTypes = database.getAllPetTypes().mapToStateFlow(
        scope = viewModelScope,
        initialValue = emptyList(),
    ) { realmPetTypes ->
        realmPetTypes.map { realmPetType ->
            PetType(
                id = realmPetType.id.toHexString(),
                name = realmPetType.name,
            )
        }
    }

    private val _state = MutableStateFlow(EditPetState(pet))
    val state = _state.asStateFlow()

    fun updatePetName(newPetName: String) {
        _state.update { state ->
            state.copy(
                petName = newPetName,
                hasPetNameWarning = false,
            )
        }
    }

    fun updatePetAge(newPetAge: String) {
        _state.update { state ->
            state.copy(
                petAge = when (newPetAge) {
                    "" -> null
                    else -> newPetAge.toIntOrNull() ?: state.petAge
                },
                hasPetAgeWarning = false,
            )
        }
    }

    fun updatePetTypeDropdownExpanded(newVisible: Boolean) {
        _state.update { state ->
            state.copy(petTypeDropdownExpanded = newVisible)
        }
    }

    fun updatePetType(newPetType: PetType) {
        _state.update { state ->
            state.copy(
                petType = newPetType,
                hasPetTypeWarning = false,
                petTypeDropdownExpanded = false,
            )
        }
    }

    fun updateOwnerName(newOwnerName: String) {
        _state.update { state ->
            state.copy(ownerName = newOwnerName)
        }
    }

    fun editPet() {
        var state = state.value
        if (state.petName.isBlank()) state = state.copy(hasPetNameWarning = true)
        if (state.petAge == null) state = state.copy(hasPetAgeWarning = true)
        if (state.petType == null) state = state.copy(hasPetTypeWarning = true)

        val newState = state
        if (!newState.hasWarning) {
            viewModelScope.launch {
                database.editPet(
                    petId = org.mongodb.kbson.ObjectId(petId),
                    name = newState.petName,
                    age = newState.petAge!!,
                    typeId = org.mongodb.kbson.ObjectId(newState.petType!!.id),
                    ownerName = newState.ownerName,
                )
                _navigateUp.emit(true)
            }
        }
        _state.update { state }
    }
}