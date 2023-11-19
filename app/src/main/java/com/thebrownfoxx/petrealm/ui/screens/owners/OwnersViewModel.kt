package com.thebrownfoxx.petrealm.ui.screens.owners

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hamthelegend.enchantmentorder.extensions.mapToStateFlow
import com.thebrownfoxx.petrealm.models.Owner
import com.thebrownfoxx.petrealm.models.Pet
import com.thebrownfoxx.petrealm.models.PetType
import com.thebrownfoxx.petrealm.realm.PetRealmDatabase

class OwnersViewModel(private val database: PetRealmDatabase) : ViewModel() {
    val owners = database.getAllOwners().mapToStateFlow(
        scope = viewModelScope,
        initialValue = emptyList(),
    ) { realmOwners ->
        realmOwners.map { realmOwner ->
            Owner(
                id = realmOwner.id.toHexString(),
                name = realmOwner.name,
                pets = realmOwner.pets.map { realmPet ->
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
                    )
                }
            )
        }
    }
}