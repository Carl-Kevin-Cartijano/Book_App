package com.thebrownfoxx.petrealm.ui.screens.pets.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hamthelegend.enchantmentorder.extensions.combineToStateFlow
import com.hamthelegend.enchantmentorder.extensions.search
import com.thebrownfoxx.petrealm.models.Owner
import com.thebrownfoxx.petrealm.models.Pet
import com.thebrownfoxx.petrealm.models.PetType
import com.thebrownfoxx.petrealm.realm.PetRealmDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PetsViewModel(private val database: PetRealmDatabase) : ViewModel() {
    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    /*val petTypes = database.getAllPetTypes().mapToStateFlow(
        scope = viewModelScope,
        initialValue = emptyList(),
    ) { realmPetTypes ->
        realmPetTypes.map { realmPetType ->
            PetType(
                id = realmPetType.id.toHexString(),
                name = realmPetType.name,
            )
        }
    }*/

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
        }.search(searchQuery) { it.name }
    }

    /*private val _addPetDialogState = MutableStateFlow<AddPetDialogState>(AddPetDialogState.Hidden)
    val addPetDialogState = _addPetDialogState.asStateFlow()*/

    private val _removePetDialogState =
        MutableStateFlow<RemovePetDialogState>(RemovePetDialogState.Hidden)
    val removePetDialogState = _removePetDialogState.asStateFlow()


    fun updateSearchQuery(newQuery: String) {
        _searchQuery.update { newQuery }
    }

    /*fun showAddPetDialog() {
        _addPetDialogState.update { state ->
            if (state == AddPetDialogState.Hidden) AddPetDialogState.Visible() else state
        }
    }

    fun hideAddPetDialog() {
        _addPetDialogState.update { state ->
            if (state is AddPetDialogState.Visible) AddPetDialogState.Hidden else state
        }
    }

    private fun updateVisibleAddPetDialogState(
        block: AddPetDialogState.Visible.() -> AddPetDialogState.Visible,
    ) {
        _addPetDialogState.update { state ->
            if (state is AddPetDialogState.Visible) state.block() else state
        }
    }

    fun updatePetName(newPetName: String) {
        updateVisibleAddPetDialogState {
            copy(
                petName = newPetName,
                hasPetNameWarning = false,
            )
        }
    }

    fun updatePetAge(newPetAge: String) {
        updateVisibleAddPetDialogState {
            copy(
                petAge = when (newPetAge) {
                    "" -> null
                    else -> newPetAge.toIntOrNull() ?: this.petAge
                },
                hasPetAgeWarning = false,
            )
        }
    }

    fun updatePetTypeDropdownExpanded(newVisible: Boolean) {
        updateVisibleAddPetDialogState { copy(petTypeDropdownExpanded = newVisible) }
    }

    fun updatePetType(newPetType: PetType) {
        updateVisibleAddPetDialogState {
            copy(
                petType = newPetType,
                hasPetTypeWarning = false,
                petTypeDropdownExpanded = false,
            )
        }
    }

    fun updateOwnerName(newOwnerName: String) {
        updateVisibleAddPetDialogState {
            copy(ownerName = newOwnerName)
        }
    }

    fun addPet() {
        var state = addPetDialogState.value
        if (state is AddPetDialogState.Visible) {
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
                state = AddPetDialogState.Hidden
            }
        }
        _addPetDialogState.update { state }
    }*/

    suspend fun initiateRemovePet(pet: Pet): Boolean {
        _removePetDialogState.update { RemovePetDialogState.Pending(pet) }
        return withContext(Dispatchers.Default) {
            var state: RemovePetDialogState
            while (true) {
                state = removePetDialogState.value
                if (state == RemovePetDialogState.Canceled || state == RemovePetDialogState.Confirmed){
                    _removePetDialogState.update { RemovePetDialogState.Hidden }
                    break
                }
            }
            return@withContext state == RemovePetDialogState.Confirmed
        }
    }

    fun cancelRemovePet() {
        _removePetDialogState.update { RemovePetDialogState.Canceled }
    }

    fun removePet() {
        val state = removePetDialogState.value
        if (state is RemovePetDialogState.Pending) {
            viewModelScope.launch {
                database.deletePet(id = org.mongodb.kbson.ObjectId(state.pet.id))
            }
        }
        _removePetDialogState.update { RemovePetDialogState.Confirmed }
    }
}