package com.thebrownfoxx.petrealm.ui.screens.pets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hamthelegend.enchantmentorder.extensions.combineToStateFlow
import com.hamthelegend.enchantmentorder.extensions.mapToStateFlow
import com.hamthelegend.enchantmentorder.extensions.search
import com.thebrownfoxx.petrealm.models.Owner
import com.thebrownfoxx.petrealm.models.Pet
import com.thebrownfoxx.petrealm.models.PetType
import com.thebrownfoxx.petrealm.realm.PetRealmDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.mongodb.kbson.ObjectId

class PetsViewModel(private val database: PetRealmDatabase) : ViewModel() {
    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

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

    val pets = combineToStateFlow(
        database.getAllPets(),
        searchQuery,
        scope = viewModelScope,
        initialValue = emptyList(),
    ) { realmPets, searchQuery ->
        realmPets.map { realmPet ->
            Pet(
                id = realmPet.id.toHexString(),
                name = realmPet.name,
                age = realmPet.age,
                type = realmPet.type?.let { realmPetType ->
                    PetType(
                        id = realmPetType.id.toHexString(),
                        name = realmPetType.name,
                    )
                },
                owner = realmPet.owner?.let { realmOwner ->
                    Owner(
                        id = realmOwner.id.toHexString(),
                        name = realmOwner.name,
                        pets = emptyList(),
                    )
                },
            )
        }.search(searchQuery) { it.name }
    }

    private val _addPetDialogState = MutableStateFlow<AddPetDialogState>(AddPetDialogState.Hidden)
    val addPetDialogState = _addPetDialogState.asStateFlow()

    private val _removePetDialogState =
        MutableStateFlow<RemovePetDialogState>(RemovePetDialogState.Hidden)
    val removePetDialogState = _removePetDialogState.asStateFlow()


    fun updateSearchQuery(newQuery: String) {
        _searchQuery.update { newQuery }
    }

    fun showAddPetDialog() {
        _addPetDialogState.update { state ->
            if (state == AddPetDialogState.Hidden) AddPetDialogState.Visible() else state
        }
    }

    fun hideAddPetDialog() {
        _addPetDialogState.update { AddPetDialogState.Hidden }
    }

    fun updatePetName(newPetName: String) {
        _addPetDialogState.update {
            if (it is AddPetDialogState.Visible) {
                it.copy(
                    petName = newPetName,
                    hasPetNameWarning = false,
                )
            } else it
        }
    }

    fun updatePetAge(newPetAge: String) {
        _addPetDialogState.update {
            if (it is AddPetDialogState.Visible) {
                val petAge = when (newPetAge) {
                    "" -> null
                    else -> newPetAge.toIntOrNull() ?: it.petAge
                }
                it.copy(
                    petAge = petAge,
                    hasPetAgeWarning = false,
                )
            } else it
        }
    }

    fun updatePetTypeDropdownExpanded(newVisible: Boolean) {
        _addPetDialogState.update {
            if (it is AddPetDialogState.Visible) {
                it.copy(petTypeDropdownExpanded = newVisible)
            } else it
        }
    }

    fun updatePetType(newPetType: PetType) {
        _addPetDialogState.update {
            if (it is AddPetDialogState.Visible) {
                it.copy(
                    petType = newPetType,
                    hasPetTypeWarning = false,
                    petTypeDropdownExpanded = false,
                )
            } else it
        }
    }

    fun updateHasOwner(newHasOwner: Boolean) {
        _addPetDialogState.update {
            if (it is AddPetDialogState.Visible) {
                it.copy(
                    hasOwner = newHasOwner,
                    ownerName = if (!newHasOwner) "" else it.ownerName,
                    hasOwnerNameWarning = if (!newHasOwner) false else it.hasOwnerNameWarning,
                )
            } else it
        }
    }

    fun updateOwnerName(newOwnerName: String) {
        _addPetDialogState.update {
            if (it is AddPetDialogState.Visible) {
                it.copy(
                    ownerName = newOwnerName,
                    hasOwnerNameWarning = false,
                )
            } else it
        }
    }

    fun addPet() {
        var state = addPetDialogState.value
        if (state is AddPetDialogState.Visible) {
            if (state.petName.isBlank()) state = state.copy(hasPetNameWarning = true)
            if (state.petAge == null) state = state.copy(hasPetAgeWarning = true)
            if (state.petType == null) state = state.copy(hasPetTypeWarning = true)
            if (state.hasOwner && state.ownerName.isBlank()) state = state.copy(hasOwnerNameWarning = true)

            val newState = state
            if (!state.hasWarning) {
                viewModelScope.launch {
                    database.addPet(
                        name = newState.petName,
                        age = newState.petAge!!,
                        typeId = ObjectId(newState.petType!!.id),
                        ownerName = newState.ownerName,
                    )
                }
                state = AddPetDialogState.Hidden
            }
        }
        _addPetDialogState.update { state }
    }

    fun initiateRemovePet(pet: Pet) {
        _removePetDialogState.update { RemovePetDialogState.Visible(pet) }
    }

    fun cancelRemovePet() {
        _removePetDialogState.update { RemovePetDialogState.Hidden }
    }

    fun removePet() {
        val state = removePetDialogState.value
        if (state is RemovePetDialogState.Visible) {
            viewModelScope.launch {
                database.deletePet(id = ObjectId(state.pet.id))
            }
        }
        _removePetDialogState.update { RemovePetDialogState.Hidden }
    }
}