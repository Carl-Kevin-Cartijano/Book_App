package com.thebrownfoxx.petrealm.realm.models

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class RealmPetType : RealmObject {
    @PrimaryKey
    var id: ObjectId = ObjectId()
    var name: String = ""
}