package com.thebrownfoxx.petrealm.ui.screens.pets.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hamthelegend.enchantmentorder.extensions.combineToStateFlow
import com.hamthelegend.enchantmentorder.extensions.search
import com.thebrownfoxx.petrealm.models.Owner
import com.thebrownfoxx.petrealm.models.Pet
import com.thebrownfoxx.petrealm.models.PetType
import com.thebrownfoxx.petrealm.realm.PetRealmDatabase
import com.thebrownfoxx.petrealm.ui.components.RemoveDialogState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PetsViewModel(private val database: PetRealmDatabase) : ViewModel() {
    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    val pets = combineToStateFlow(
        database.getAllPets(),
        searchQuery,
        scope = viewModelScope,
        initialValue = null,
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

    private val _selectedPet = MutableStateFlow<Pet?>(null)
    val selectedPet = _selectedPet.asStateFlow()

    private val _adoptDialogState =
        MutableStateFlow<AdoptDialogState>(AdoptDialogState.Hidden)
    val adoptDialogState = _adoptDialogState.asStateFlow()

    private val _removeDialogState =
        MutableStateFlow<RemoveDialogState<Pet>>(RemoveDialogState.Hidden())
    val removeDialogState = _removeDialogState.asStateFlow()

    fun updateSearchQuery(newQuery: String) {
        _searchQuery.update { newQuery }
    }

    fun updateSelectedPet(pet: Pet?) {
        _selectedPet.update { pet }
    }

    fun initiateAdopt(pet: Pet) {
        _adoptDialogState.update { AdoptDialogState.Pending(pet) }
    }

    fun updateOwnerName(newOwnerName: String) {
        _adoptDialogState.update {
            if (it is AdoptDialogState.Pending) {
                it.copy(
                    ownerName = newOwnerName,
                    hasOwnerNameWarning = false,
                )
            } else it
        }
    }

    fun cancelAdopt() {
        _adoptDialogState.update { AdoptDialogState.Hidden }
    }

    fun adopt() {
        var state = adoptDialogState.value
        if (state is AdoptDialogState.Pending) {
            if (state.ownerName.isBlank()) state = state.copy(hasOwnerNameWarning = true)

            val newState = state
            if (!newState.hasOwnerNameWarning) {
                viewModelScope.launch {
                    database.adoptPet(
                        petId = org.mongodb.kbson.ObjectId(newState.pet.id),
                        ownerName = newState.ownerName,
                    )
                }
                state = AdoptDialogState.Hidden
            }
        }
        _adoptDialogState.update { state }
    }

    suspend fun initiateRemove(pet: Pet): Boolean {
        _removeDialogState.update { RemoveDialogState.Pending(pet) }
        return withContext(Dispatchers.Default) {
            var state: RemoveDialogState<Pet>
            while (true) {
                state = removeDialogState.value
                if (state is RemoveDialogState.Canceled || state is RemoveDialogState.Confirmed){
                    _removeDialogState.update { RemoveDialogState.Hidden() }
                    break
                }
            }
            return@withContext state is RemoveDialogState.Confirmed
        }
    }

    fun cancelRemove() {
        _removeDialogState.update { RemoveDialogState.Canceled() }
    }

    fun remove() {
        val state = removeDialogState.value
        if (state is RemoveDialogState.Pending) {
            viewModelScope.launch {
                database.deletePet(id = org.mongodb.kbson.ObjectId(state.value.id))
            }
        }
        _removeDialogState.update { RemoveDialogState.Confirmed() }
    }
}