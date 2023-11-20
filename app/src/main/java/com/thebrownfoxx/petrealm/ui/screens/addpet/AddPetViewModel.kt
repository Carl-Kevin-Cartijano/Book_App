package com.thebrownfoxx.petrealm.ui.screens.addpet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hamthelegend.enchantmentorder.extensions.mapToStateFlow
import com.thebrownfoxx.petrealm.models.PetType
import com.thebrownfoxx.petrealm.realm.PetRealmDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddPetViewModel(private val database: PetRealmDatabase) : ViewModel() {
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

    private val _state = MutableStateFlow(AddPetState())
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

    fun addPet() {
        var state = _state.value
        if (state.petName.isBlank()) state = state.copy(hasPetNameWarning = true)
        if (state.petAge == null) state = state.copy(hasPetAgeWarning = true)
        if (state.petType == null) state = state.copy(hasPetTypeWarning = true)

        val newState = state
        if (!state.hasWarning) {
            viewModelScope.launch {
                database.addPet(
                    name = newState.petName,
                    age = newState.petAge!!,
                    typeId = org.mongodb.kbson.ObjectId(newState.petType!!.id),
                    ownerName = newState.ownerName,
                )
            }
        }
        _state.update { state }
    }
}