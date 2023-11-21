package com.thebrownfoxx.petrealm.ui.screens.owners

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

class OwnersViewModel(private val database: PetRealmDatabase) : ViewModel() {
    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    val owners = combineToStateFlow(
        database.getAllOwners(),
        searchQuery,
        scope = viewModelScope,
        initialValue = emptyList(),
    ) { realmOwners, searchQuery ->
        realmOwners.map { realmOwner ->
            Owner(
                id = realmOwner.id.toHexString(),
                name = realmOwner.name,
                pets = realmOwner.pets.map { realmPet ->
                    Pet(
                        id = realmPet.id.toHexString(),
                        name = realmPet.name,
                        age = realmPet.age,
                        type = PetType(
                            id = realmPet.type!!.id.toHexString(),
                            name = realmPet.type!!.name,
                        ),
                    )
                }
            )
        }.search(searchQuery) { it.name }
    }

    private val _selectedOwner = MutableStateFlow<Owner?>(null)
    val selectedOwner = _selectedOwner.asStateFlow()

    private val _removeDialogState =
        MutableStateFlow<RemoveDialogState<Owner>>(RemoveDialogState.Hidden<Owner>())
    val removeDialogState = _removeDialogState.asStateFlow()

    fun updateSearchQuery(newQuery: String) {
        _searchQuery.update { newQuery }
    }

    fun updateSelectedOwner(owner: Owner?) {
        _selectedOwner.update { owner }
    }

    suspend fun initiateRemove(owner: Owner): Boolean {
        _removeDialogState.update { RemoveDialogState.Pending(owner) }
        return withContext(Dispatchers.Default) {
            var state: RemoveDialogState<Owner>
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
                database.deleteOwner(id = org.mongodb.kbson.ObjectId(state.value.id))
            }
        }
        _removeDialogState.update { RemoveDialogState.Confirmed() }
    }
}