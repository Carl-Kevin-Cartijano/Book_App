package com.thebrownfoxx.petrealm.realm.models

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class RealmPet: RealmObject {
    @PrimaryKey
    var id: ObjectId = ObjectId()
    var name: String = ""
    var age: Int = 0
    var type: RealmPetType? = null
    var owner: RealmOwner? = null
}