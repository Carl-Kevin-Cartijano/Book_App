package com.thebrownfoxx.petrealm.realm

import com.thebrownfoxx.petrealm.realm.models.RealmOwner
import com.thebrownfoxx.petrealm.realm.models.RealmPet
import com.thebrownfoxx.petrealm.realm.models.RealmPetType
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.mongodb.kbson.ObjectId

class PetRealmDatabase {
    private val config = RealmConfiguration.Builder(
        schema = setOf(RealmPet::class, RealmOwner::class, RealmPetType::class)
    ).schemaVersion(1)
        .initialData {
            copyToRealm(RealmPetType().apply { name = "Cat" })
            copyToRealm(RealmPetType().apply { name = "Dog" })
        }
        .build()
    private val realm: Realm = Realm.open(config)

    fun getAllPetTypes(): Flow<List<RealmPetType>> =
        realm.query<RealmPetType>().asFlow().map { it.list }

    fun getAllPets(): Flow<List<RealmPet>> = realm.query<RealmPet>().asFlow().map { it.list }

    suspend fun addPet(
        name: String,
        age: Int,
        typeId: ObjectId,
        ownerName: String = "",
    ) {
        withContext(Dispatchers.IO) {
            realm.write {
                val petType = realm.query<RealmPetType>("id == $0", typeId).first().find()
                val pet = RealmPet().apply {
                    this.name = name
                    this.age = age
                    this.type = petType?.let { findLatest(petType) }
                }
                val managedPet = copyToRealm(pet)
                if (ownerName.isNotEmpty()) {
                    val ownerResult: RealmOwner? =
                        realm.query<RealmOwner>("name == $0", ownerName).first().find()
                    if (ownerResult == null) {
                        val owner = RealmOwner().apply {
                            this.name = ownerName
                            this.pets.add(managedPet)
                        }
                        val managedOwner = copyToRealm(owner)
                        managedPet.owner = managedOwner
                    } else {
                        findLatest(ownerResult)?.pets?.add(managedPet)
                        findLatest(managedPet)?.owner = findLatest(ownerResult)
                    }
                }
            }
        }
    }

    suspend fun deletePet(id: ObjectId) {
        withContext(Dispatchers.IO) {
            realm.write {
                query<RealmPet>("id == $0", id)
                    .first()
                    .find()
                    ?.let { delete(it) }
                    ?: throw IllegalStateException("Pet not found!")
            }
        }
    }

    fun getAllOwners(): Flow<List<RealmOwner>> = realm.query<RealmOwner>().asFlow().map { it.list }
}